package net.sakuragame.eternal.kirracore.bungee.manager;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

@AllArgsConstructor
public class ServerManager {

    private KirraCoreBungee instance;

    @Getter
    private final HashMap<String, ServerInfo> servers = new HashMap<>();

    @Nullable
    public ServerInfo getFirst(@NotNull String prefix) {
        Optional<String> name = servers.keySet()
                .stream()
                .filter(s -> s.toLowerCase().contains(prefix.toLowerCase()))
                .findAny();
        return name.map(servers::get).orElse(null);
    }

    @Nullable
    public ServerInfo getByBalancing(@NotNull String prefix) {
        Optional<ServerInfo> info = servers.values()
                .stream()
                .filter(s -> s.getName().toLowerCase().contains(prefix.toLowerCase()))
                .min(Comparator.comparingInt(i -> i.getPlayers().size()));
        return info.orElse(null);
    }
}
