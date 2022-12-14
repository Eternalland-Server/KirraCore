package me.skymc.taskchain;

import lombok.AllArgsConstructor;
import me.skymc.taskchain.TaskChainFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

@AllArgsConstructor
public class TaskChainListener implements Listener {

    private final TaskChainFactory factory;

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals(factory.getInstance().getName())) {
            factory.getScheduler().shutdownNow();
        }
    }
}