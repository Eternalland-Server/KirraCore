package net.sakuragame.eternal.kirracore.bukkit.command.management;

import com.qrakn.honcho.command.CommandMeta;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;

@CommandMeta(label = {"restart", "Restart"}, permission = "admin")
public class CommandRestart {

    public void execute(int delaySeconds, String reason) {
        FunctionRestart.execute(delaySeconds, reason);
    }
}
