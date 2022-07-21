package me.skymc.taskchain;

import lombok.Getter;
import me.skymc.taskchain.TaskChain;
import me.skymc.taskchain.TaskChainListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

public class TaskChainFactory {

    @Getter
    private final JavaPlugin instance;

    @Getter
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public TaskChainFactory(JavaPlugin plugin) {
        this.instance = plugin;
        Bukkit.getPluginManager().registerEvents(new TaskChainListener(this), instance);
    }

    public me.skymc.taskchain.TaskChain newChain() {
        return new TaskChain(instance, scheduler, new LinkedBlockingQueue<>());
    }
}