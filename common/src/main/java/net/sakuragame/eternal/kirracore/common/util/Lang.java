package net.sakuragame.eternal.kirracore.common.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.sakuragame.eternal.kirracore.common.util.CC.toColored;

public class Lang {

    public static final String NORMAL_MSG_PREFIX = toColored("&6&l➱ &e");
    public static final String NOTICE_MSG_PREFIX = toColored("&5&l➱ &d");
    public static final String WARN_MSG_PREFIX = toColored("&4&l➱ &c");

    public static final String MANAGEMENT_MSG_PREFIX = toColored("&c[System] &7");

    public enum BukkitMessageType {
        TEXT, ACTION_BAR;
    }

    public static void sendMessage(CommandSender sender, String str, BukkitMessageType type) {
        String coloredStr = toColored(str);
        switch (type) {
            case TEXT:
                sender.sendMessage(coloredStr);
                break;
            case ACTION_BAR:
                Player player = TypeUtil.safeCast(sender, Player.class);
                if (player == null) {
                    return;
                }
                MessageAPI.sendActionTip(player, coloredStr);
                break;
        }
    }

    public static void sendMessage(CommandSender sender, String prefix, String str, BukkitMessageType type) {
        str = prefix + str;
        sendMessage(sender, str, type);
    }

    public static void sendMessage(net.md_5.bungee.api.CommandSender sender, String str) {
        BaseComponent[] component = new ComponentBuilder(toColored(str))
                .create();
        sender.sendMessage(component);
    }
}
