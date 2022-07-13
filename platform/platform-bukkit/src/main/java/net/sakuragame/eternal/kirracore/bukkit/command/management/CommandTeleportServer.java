package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
@CommandMeta(label = {"teleportServer", "tpServer"}, permission = "admin", async = true)
public class CommandTeleportServer {

    public void execute(Player player, String serverIDOrPrefix, String assignWorld, String assignCoord) {

    }
}