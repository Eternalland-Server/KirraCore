package net.sakuragame.eternal.kirracore.bukkit;

public class KConfiguration {

    public static boolean isTeleportWhenJoin() {
        return KirraCoreBukkit.getInstance().getConfig().getBoolean("settings.teleport-when-join");
    }

    public static boolean isAllowedPvP() {
        return KirraCoreBukkit.getInstance().getConfig().getBoolean("settings.allowed-pvp");
    }

    public static boolean isParentServer() {
        return KirraCoreBukkit.getInstance().getConfig().getBoolean("settings.parent-server");
    }
}