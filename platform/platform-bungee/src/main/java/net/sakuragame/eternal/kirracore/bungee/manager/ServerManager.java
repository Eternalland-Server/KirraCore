package net.sakuragame.eternal.kirracore.bungee.manager;

import lombok.Getter;
import lombok.val;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.event.ServerOfflineEvent;
import net.sakuragame.eternal.kirracore.bungee.event.ServerOnlineEvent;
import net.sakuragame.eternal.kirracore.bungee.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;

public class ServerManager {

    @Getter
    private final KirraCoreBungee plugin;

    @Getter
    private final HashMap<String, ServerInfo> servers = new HashMap<>();

    public ServerManager(KirraCoreBungee plugin) {
        this.plugin = plugin;
    }

    public void setOnline(@NotNull String serverID) {
        val server = Utils.getServerInfoByID(serverID);
        if (server == null) {
            return;
        }
        servers.put(serverID, server);
        KirraCoreBungee.getInstance().getProxy().getPluginManager().callEvent(new ServerOnlineEvent(serverID));
    }

    public void setOffline(@NotNull String serverID) {
        val server = Utils.getServerInfoByID(serverID);
        if (server == null) {
            return;
        }
        servers.remove(serverID);
        KirraCoreBungee.getInstance().getProxy().getPluginManager().callEvent(new ServerOfflineEvent(serverID));
    }

    @Nullable
    public ServerInfo getFirstOrNull(@NotNull String serverPrefix) {
        val name = servers.keySet()
                .stream()
                .filter(s -> s.toLowerCase().contains(serverPrefix.toLowerCase()))
                .findAny();
        return name.map(servers::get).orElse(null);
    }

    @Nullable
    public ServerInfo getByBalancing(@NotNull String serverPrefix) {
        val info = servers.values()
                .stream()
                .filter(s -> s.getName().toLowerCase().contains(serverPrefix.toLowerCase()))
                .min(Comparator.comparingInt(i -> i.getPlayers().size()));
        return info.orElse(null);
    }
}
