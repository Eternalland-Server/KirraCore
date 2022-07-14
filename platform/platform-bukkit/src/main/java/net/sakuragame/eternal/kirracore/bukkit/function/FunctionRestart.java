package net.sakuragame.eternal.kirracore.bukkit.function;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.annotation.KListener;
import net.sakuragame.eternal.kirracore.bukkit.util.Broadcast;
import net.sakuragame.eternal.kirracore.bukkit.util.taskchain.TaskChain;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.atomic.AtomicInteger;

@KListener
public class FunctionRestart implements Listener {

    private static boolean RESTARTING = false;

    public static void execute(int delaySeconds, String reason) {
        if (RESTARTING) {
            KirraCoreBukkit.getInstance().getLogger().warning("无法同时进行多个重启任务.");
            return;
        }
        RESTARTING = true;
        val i = new AtomicInteger(delaySeconds);
        new TaskChain()
                .delay(20)
                .task(() -> {
                    Broadcast.send(Lang.NOTICE_MSG_PREFIX + "服务器即将重启!");
                    Broadcast.send(Lang.NOTICE_MSG_PREFIX + "原因: &f" + reason);
                }, true)
                .delay(5)
                .repeatedTask(() -> {
                    val secs = i.decrementAndGet();
                    if (secs < 20 && secs % 5 == 0) {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                            player.sendTitle("", CC.toColored("&7&o服务器还有 " + secs + " 秒重启."), 0, 25, 0);
                        });
                    }
                }, () -> i.get() < 1, 20)
                .task(() -> Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(Bukkit.getShutdownMessage())))
                .delayedTask(Bukkit::shutdown, 100)
                .execute();
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (RESTARTING) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Bukkit.getShutdownMessage());
        }
    }
}
