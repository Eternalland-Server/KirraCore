package net.sakuragame.eternal.kirracore.bungee.listener;

import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.annotation.KListener;

@KListener
public class ListenerBungeeKick implements Listener {

    // 在之后会优化逻辑, 目前能用就行.
    @EventHandler
    public void onServerKickEvent(ServerKickEvent e) {
        ServerInfo kickedFrom;
        ServerInfo kickTo;
        if (e.getPlayer().getServer() != null) {
            kickedFrom = e.getPlayer().getServer().getInfo();
        } else if (KirraCoreBungee.getInstance().getProxy().getReconnectHandler() != null) {
            kickedFrom = KirraCoreBungee.getInstance().getProxy().getReconnectHandler().getServer(e.getPlayer());
        } else {
            kickedFrom = AbstractReconnectHandler.getForcedHost(e.getPlayer().getPendingConnection());
            if (kickedFrom == null) {
                kickedFrom = ProxyServer.getInstance().getServerInfo(e.getPlayer().getPendingConnection().getListener().getDefaultServer());
            }
        }
        if (kickedFrom.getName().toLowerCase().contains("rpg-login")) {
            return;
        }
        if (kickedFrom.getName().toLowerCase().contains("rpg-spawn")) {
            kickTo = KirraCoreBungee.getInstance().getProxy().getServerInfo("rpg-login-1");
        } else {
            ServerInfo hubServer = KirraCoreBungee.getInstance().getServerManager().byBalancing("rpg-spawn");
            kickTo = (hubServer == null) ? KirraCoreBungee.getInstance().getProxy().getServerInfo("rpg-login-1") : hubServer;
        }
        if (kickedFrom.equals(kickTo)) {
            return;
        }
        e.setCancelled(true);
        e.setCancelServer(kickTo);
    }
}
