package net.sakuragame.eternal.kirracore.bukkit.listener;

import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import net.sakuragame.serversystems.manage.client.api.event.NewDayEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@KListener
public class ListenerRestart implements Listener {

    static {
        Scheduler.runLaterAsync(() -> Utils.info("已设置在凌晨 0 点的重启计划."), 60);
    }

    @EventHandler
    public void onDay(NewDayEvent event) {
        FunctionRestart.execute(30, "定时任务");
    }
}