package net.sakuragame.eternal.kirracore.bukkit.util.taskchain;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface ITask {

    boolean async();

    @NotNull CompletableFuture<Boolean> execute();
}