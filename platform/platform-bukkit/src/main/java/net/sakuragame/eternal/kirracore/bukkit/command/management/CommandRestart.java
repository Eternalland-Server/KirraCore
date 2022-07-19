package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import org.bukkit.command.CommandSender;

@CommandMeta(label = {"kRestart"}, permission = "admin")
public class CommandRestart {

    public void execute(CommandSender sender, long delaySeconds, String reason) {
        FunctionRestart.execute((int) delaySeconds, reason);
    }
}