package net.sakuragame.eternal.kirracore.bungee.listener;

import lombok.val;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.sakuragame.eternal.kirracore.bungee.function.FunctionPacket;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;

@KListener
public class ListenerPlayer implements Listener {

    @EventHandler
    public void onStaffJoin(ServerConnectedEvent event) {
        val player = event.getPlayer();
        if (player.hasPermission("admin")) {
            FunctionPacket.sendStaffJoinOrQuitPacket(player.getName(), event.getServer().getInfo().getName(), true);
        }
    }

    @EventHandler
    public void onStaffLeft(ServerDisconnectEvent event) {
        val player = event.getPlayer();
        if (player.hasPermission("admin")) {
            FunctionPacket.sendStaffJoinOrQuitPacket(player.getName(), event.getTarget().getName(),false);
        }
    }
}