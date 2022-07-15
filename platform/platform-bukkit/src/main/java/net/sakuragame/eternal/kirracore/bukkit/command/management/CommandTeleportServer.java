package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
@CommandMeta(label = {"teleportServer", "tpServer"}, permission = "admin", async = true)
public class CommandTeleportServer {

    public void execute(Player player, String serverIDOrPrefix) {
        KirraCoreBukkitAPI.teleportPlayerToServerByBalancing(serverIDOrPrefix, player.getUniqueId());
    }

//    public void execute(Player player, String serverIDOrPrefix, String assignWorld) {
//        KirraCoreBukkitAPI.teleportPlayerToAnotherServer(serverIDOrPrefix, assignWorld, new UUID[]{player.getUniqueId()});
//    }
//
//    public void execute(Player player, String serverIDOrPrefix, String assignWorld, String assignCoord) {
//        KirraCoreBukkitAPI.teleportPlayerToAnotherServer(serverIDOrPrefix, assignWorld, assignCoord, new UUID[]{player.getUniqueId()});
//    }
}