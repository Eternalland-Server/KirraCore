package net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.ITask;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class SingleTask implements ITask {

    @Getter
    private Runnable runnable;

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
            Bukkit.getScheduler().runTaskAsynchronously(KirraCoreBukkit.getInstance(), runnable);
        } else {
            Bukkit.getScheduler().runTask(KirraCoreBukkit.getInstance(), runnable);
        }
        future.complete(true);
        return future;
    }
}
