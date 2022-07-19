package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
@CommandMeta(label = {"teleportServer", "tpServer"}, permission = "admin", async = true)
public class CommandTeleportServer {

    public void execute(CommandSender sender, String playerID, String serverIDOrPrefix) {
        val player = Bukkit.getPlayerExact(playerID);
        if (player == null) {
            return;
        }
        execute(player, serverIDOrPrefix);
    }

    public void execute(CommandSender sender, String playerID, String serverIDOrPrefix, String assignType, String assignValue) {
        val player = Bukkit.getPlayerExact(playerID);
        if (player == null) {
            return;
        }
        execute(player, serverIDOrPrefix, assignType, assignValue);
    }

    public void execute(Player player, String serverIDOrPrefix) {
        KirraCoreBukkitAPI.teleportPlayerToServerByBalancing(serverIDOrPrefix, player.getUniqueId());
    }

    public void execute(Player player, String serverIDOrPrefix, String assignType, String assignValue) {
        switch (assignType.toLowerCase()) {
            case "world": {
                KirraCoreBukkitAPI.teleportPlayerToAnotherServer(serverIDOrPrefix, AssignType.ASSIGN_WORLD, assignValue, player.getUniqueId());
            }
            case "coord": {
                KirraCoreBukkitAPI.teleportPlayerToAnotherServer(serverIDOrPrefix, AssignType.ASSIGN_COORD, assignValue, player.getUniqueId());
            }
            default: {
                KirraCoreBukkitAPI.teleportPlayerToServerByBalancing(serverIDOrPrefix, player.getUniqueId());
            }
        }
    }
}