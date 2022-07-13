package net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.ITask;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class RepeatedTask implements ITask {

    @Getter
    private Runnable runnable;

    @Getter
    private Callable<Boolean> predicate;

    @Getter
    private long period;

    @Getter
    private boolean async;

    @Override
    public boolean async() {
        return async;
    }

    @Override
    public @NotNull CompletableFuture<Boolean> execute() {
        val future = new CompletableFuture<Boolean>();
        if (async) {
            new BukkitRunnable() {

                @SneakyThrows
                @Override
                public void run() {
                    if (!predicate.call()) {
                        this.cancel();
                        future.complete(true);
                    }
                    runnable.run();
                }
            }.runTaskTimerAsynchronously(KirraCoreBukkit.getInstance(), 0L, period);
        } else {
            new BukkitRunnable() {

                @SneakyThrows
                @Override
                public void run() {
                    if (!predicate.call()) {
                        this.cancel();
                        future.complete(true);
                    }
                    runnable.run();
                }
            }.runTaskTimer(KirraCoreBukkit.getInstance(), 0L, period);
        }
        return future;
    }
}
