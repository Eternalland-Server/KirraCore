package net.sakuragame.eternal.kirracore.bungee.manager;

import lombok.Getter;
import lombok.val;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
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
    }

    public void setOffline(@NotNull String serverID) {
        servers.remove(serverID);
    }

    @Nullable
    public ServerInfo firstOrNull(@NotNull String serverPrefix) {
        val name = servers.keySet()
                .stream()
                .filter(s -> s.equalsIgnoreCase(serverPrefix))
                .findAny();
        return name.map(servers::get).orElse(null);
    }

    @Nullable
    public ServerInfo byBalancing(@NotNull String serverPrefix) {
        val info = servers.values()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(serverPrefix))
                .min(Comparator.comparingInt(i -> i.getPlayers().size()));
        return info.orElse(null);
    }
}
