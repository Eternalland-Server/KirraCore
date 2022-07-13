package net.sakuragame.eternal.kirracore.bukkit.command.management;

import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.TaskChain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandTest {

    public void execute(Player player) {
        new TaskChain()
                .appendDelay(20)
                .appendTask(() -> {
                    Bukkit.broadcastMessage(player.getName() + " 真是一个傻逼啊!");
                    Bukkit.broadcastMessage("三秒后我要给他一个惊喜!");
                }, true)
                .appendDelay(60)
                .appendTask(() -> player.getInventory().clear(), false)
                .start();
    }
}
