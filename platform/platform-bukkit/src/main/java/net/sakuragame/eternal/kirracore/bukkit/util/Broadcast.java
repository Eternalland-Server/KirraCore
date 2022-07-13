package net.sakuragame.eternal.kirracore.bukkit.util;

import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class Broadcast {

    public static void send(@NotNull String str) {
        Bukkit.broadcastMessage(CC.toColored(str));
    }

    public static void send(@NotNull String str, @NotNull Predicate<Player> predicate) {
        Bukkit.getOnlinePlayers()
                .stream()
                .filter(predicate)
                .forEach(player -> Lang.sendMessage(player, str, Lang.BukkitMessageType.TEXT));
    }
}