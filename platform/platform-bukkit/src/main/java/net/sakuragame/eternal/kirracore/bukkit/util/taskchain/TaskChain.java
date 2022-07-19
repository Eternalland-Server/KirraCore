package net.sakuragame.eternal.kirracore.bukkit.util.taskchain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.PureDelayedTask;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.RepeatedTask;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl.SingleTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnusedReturnValue")
@RequiredArgsConstructor
public class TaskChain {

    @Getter
    private static final Plugin INSTANCE = KirraCoreBukkit.getInstance();

    @Getter
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    @Getter
    Queue<ITask> tasks = new ArrayDeque<>();

    @NotNull
    public TaskChain delay(long delay) {
        tasks.add(new PureDelayedTask(delay));
        return this;
    }

    @NotNull
    public TaskChain delayedTask(@NotNull Runnable runnable, long delay) {
        delay(delay);
        task(runnable);
        return this;
    }

    @NotNull
    public TaskChain delayedTask(@NotNull Runnable runnable, long delay, boolean async) {
        delay(delay);
        task(runnable, async);
        return this;
    }

    @NotNull
    public TaskChain task(@NotNull Runnable runnable) {
        tasks.add(new SingleTask(runnable, false));
        return this;
    }

    @NotNull
    public TaskChain task(@NotNull Runnable runnable, boolean async) {
        tasks.add(new SingleTask(runnable, async));
        return this;
    }

    @NotNull
    public TaskChain repeatedTask(@NotNull Runnable runnable, @NotNull Callable<Boolean> predicate, long period) {
        tasks.add(new RepeatedTask(runnable, predicate, period, false));
        return this;
    }

    @NotNull
    public TaskChain repeatedTask(@NotNull Runnable runnable, @NotNull Callable<Boolean> predicate, long period, boolean async) {
        tasks.add(new RepeatedTask(runnable, predicate, period, async));
        return this;
    }

    public void execute() {
        Bukkit.broadcastMessage(tasks.toString());
        SCHEDULER.execute(() -> {
            val task = tasks.poll();
            Bukkit.broadcastMessage("poll");
            if (task == null) {
                Bukkit.broadcastMessage("null");
                return;
            }
            if (task instanceof PureDelayedTask) {
                val delayedTask = (PureDelayedTask) task;
                Bukkit.broadcastMessage("delay");
                Bukkit.broadcastMessage("delay: " + delayedTask.getDelay());
                SCHEDULER.schedule(task::execute, delayedTask.getDelay(), TimeUnit.MILLISECONDS);
                return;
            }
            Bukkit.broadcastMessage("whenComplete");
            task.execute().whenComplete((bool, throwable) -> {
                if (bool) {
                    Bukkit.broadcastMessage("do recursive");
                    execute();
                }
            });
        });
    }
}