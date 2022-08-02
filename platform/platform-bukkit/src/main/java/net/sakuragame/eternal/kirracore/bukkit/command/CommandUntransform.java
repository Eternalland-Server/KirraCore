package net.sakuragame.eternal.kirracore.bukkit.command;

import com.qrakn.honcho.command.CommandMeta;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@SuppressWarnings("SpellCheckingInspection")
@CommandMeta(label = "untransform", async = true)
public class CommandUntransform {

    public void execute(CommandSender sender, String playerID) {
        val player = Bukkit.getPlayer(playerID);
        if (player == null) {
            return;
        }
        val profile = KirraCoreBukkit.getInstance().getProfileManager().getProfile(player);
        if (profile == null) {
            return;
        }
        profile.setConvertModel(null);
        profile.convertMinutes = 0;
        profile.loadConvertData();
    }
}
