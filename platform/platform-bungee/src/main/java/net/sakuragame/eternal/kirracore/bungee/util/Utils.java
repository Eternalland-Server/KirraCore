package net.sakuragame.eternal.kirracore.bungee.util;

import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Utils {

    @Nullable
    public static ServerInfo getServerInfoByID(@NotNull String serverID) {
        return KirraCoreBungee.getInstance().getProxy().getServersCopy().values()
                .stream()
                .filter(s -> s.getName().equalsIgnoreCase(serverID))
                .findFirst()
                .orElse(null);
    }

    public static void printToConsole(String messages) {
        KirraCoreBungee.getInstance().getProxy().getLogger().info(messages);
    }
}