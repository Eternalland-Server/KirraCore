package net.sakuragame.eternal.kirracore.bukkit.util;

import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

@SuppressWarnings("UnusedReturnValue")
public class Scheduler {

    private static final KirraCoreBukkit INSTANCE = KirraCoreBukkit.getInstance();

    public static BukkitTask run(Runnable runnable) {
        return Bukkit.getScheduler().runTask(INSTANCE, runnable);
    }

    public static BukkitTask runAsync(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(INSTANCE, runnable);
    }

    public static BukkitTask runLater(Runnable runnable, int tick) {
        return Bukkit.getScheduler().runTaskLater(INSTANCE, runnable, tick);
    }

    public static BukkitTask runLaterAsync(Runnable runnable, int tick) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(INSTANCE, runnable, tick);
    }

    public static int runRepeat(Runnable runnable, int delay, int period) {
       return Bukkit.getScheduler().scheduleSyncRepeatingTask(INSTANCE, runnable, delay, period);
    }

    public static int runRepeatAsync(Runnable runnable, int delay, int period) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(INSTANCE, runnable, delay, period);
    }
}
