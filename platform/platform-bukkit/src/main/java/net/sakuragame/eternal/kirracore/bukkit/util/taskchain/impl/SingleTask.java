package net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.ITask;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.TaskChain;
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
    @NotNull
    public CompletableFuture<Boolean> execute() {
        val future = new CompletableFuture<Boolean>();
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(TaskChain.getINSTANCE(), runnable);
        } else {
            Bukkit.getScheduler().runTask(TaskChain.getINSTANCE(), runnable);
        }
        future.complete(true);
        return future;
    }
}
