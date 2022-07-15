package net.sakuragame.eternal.kirracore.bungee.listener;

import lombok.val;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.event.ServerOfflineEvent;
import net.sakuragame.eternal.kirracore.bungee.event.ServerOnlineEvent;
import net.sakuragame.eternal.kirracore.bungee.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Lang;

@KListener
public class ListenerServer implements Listener {

    @EventHandler
    public void onServerOnline(ServerOnlineEvent event) {
        val serverID = event.getServerID();
        Utils.printToConsole("[ServerManager] 服务器 " + serverID + " 上线了.");
        KirraCoreBungee.getInstance().getProxy().getPlayers()
                .stream()
                .filter(p -> p.hasPermission("admin"))
                .forEach(player -> Lang.sendMessage(player, CC.toColored("&c[System] &7服务器 " + serverID + " 上线了.")));
    }

    @EventHandler
    public void onServerOffline(ServerOfflineEvent event) {
        val serverID = event.getServerID();
        Utils.printToConsole("[ServerManager] 服务器 " + serverID + " 下线了.");
        KirraCoreBungee.getInstance().getProxy().getPlayers()
                .stream()
                .filter(p -> p.hasPermission("admin"))
                .forEach(player -> Lang.sendMessage(player, CC.toColored("&c[System] &7服务器 " + serverID + " 下线了.")));
    }
}