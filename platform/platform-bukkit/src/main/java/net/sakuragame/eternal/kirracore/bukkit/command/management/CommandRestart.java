package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandMeta(label = {"kRestart"}, permission = "admin")
public class CommandRestart {

    public void execute(Player player, Long delaySeconds, String reason) {
        FunctionRestart.execute(Integer.parseInt(delaySeconds.toString()), reason);
    }

    public void execute(CommandSender sender, Long delaySeconds, String reason) {
        FunctionRestart.execute(Integer.parseInt(delaySeconds.toString()), reason);
    }
}