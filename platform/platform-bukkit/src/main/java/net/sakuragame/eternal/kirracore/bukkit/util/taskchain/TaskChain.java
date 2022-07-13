package net.sakuragame.eternal.kirracore.bukkit.util.taskchain;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.PureDelayedTask;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.RepeatedTask;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.SingleTask;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskChain {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    Queue<ITask> tasks;

    public TaskChain appendDelay(long delay) {
        tasks.add(new PureDelayedTask(delay));
        return this;
    }

    public TaskChain appendDelayedTask(Runnable runnable, long delay, boolean async) {
        appendDelay(delay);
        appendTask(runnable, async);
        return this;
    }

    public TaskChain appendTask(Runnable runnable, boolean async) {
        tasks.add(new SingleTask(runnable, async));
        return this;
    }

    public TaskChain appendRepeatedTask(Runnable runnable, Callable<Boolean> predicate, long period, boolean async) {
        tasks.add(new RepeatedTask(runnable, predicate, period, async));
        return this;
    }

    public void start() {
        SCHEDULER.execute(() -> {
            val task = tasks.poll();
            if (task == null) {
                return;
            }
            if (task instanceof PureDelayedTask) {
                SCHEDULER.scheduleWithFixedDelay(task::execute, 0, ((PureDelayedTask) task).getDelay(), TimeUnit.MILLISECONDS);
                return;
            }
            task.execute().whenComplete((bool, throwable) -> {
                if (bool) {
                    start();
                }
            });
        });
    }
}