package net.sakuragame.eternal.kirracore.bukkit.command;

import com.qrakn.honcho.command.CommandMeta;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = "hub", async = true)
public class CommandHub {

    public void execute(CommandSender sender, String playerID) {
        val player = Bukkit.getPlayerExact(playerID);
        if (player == null) {
            return;
        }
        KirraCoreBukkitAPI.teleportPlayerToServerByBalancing("rpg-spawn", player.getUniqueId());
    }

    public void execute(Player player) {
        KirraCoreBukkitAPI.teleportPlayerToServerByBalancing("rpg-spawn", player.getUniqueId());
    }
}