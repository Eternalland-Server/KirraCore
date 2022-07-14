package net.sakuragame.eternal.kirracore.bukkit.util.taskchain.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.ITask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PureDelayedTask implements ITask {

    @Getter
    private long delay;

    @Override
    public boolean async() {
        return true;
    }

    @Override
    @NotNull
    public CompletableFuture<Boolean> execute() {
        val future = new CompletableFuture<Boolean>();
        future.complete(true);
        return future;
    }
}
