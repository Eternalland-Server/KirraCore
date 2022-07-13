package net.sakuragame.eternal.kirracore.bukkit.util;

import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class LocationUtil {

    public static void sparkyThings(@NotNull Player player) {
        new BukkitRunnable() {
            int tick = 0;
            double radius = 4;
            double angle = 0;

            @SuppressWarnings("IntegerDivisionInFloatingPointContext")
            @Override
            public void run() {
                double rad = Math.toRadians(angle);
                double x = radius * Math.cos(rad);
                double z = radius * Math.sin(rad);
                tick++;

                radius -= 0.05;
                angle += 5;
                if (angle == 720) {
                    cancel();
                    return;
                }
                Location spawnLocation = player.getLocation().clone().add(x, 0.5, z);
                for (double i = 0; i < 360; i += 360 / 10) {
                    Location loc = rotateLocationAboutPoint(spawnLocation, i, player.getLocation());
                    player.spawnParticle(Particle.END_ROD, loc, 1, 0, 0, 0, 0.02);
                    player.spawnParticle(Particle.FLAME, loc, 1, 0, 0, 0, 0.005);
                }
                player.spawnParticle(Particle.END_ROD, spawnLocation, 1, 0, 0, 0, 0.02);
                player.spawnParticle(Particle.FLAME, spawnLocation, 1, 0, 0, 0, 0.005);
            }
        }.runTaskTimerAsynchronously(KirraCoreBukkit.getInstance(), 0L, 1L);
    }

    public static Location rotateLocationAboutPoint(@NotNull Location location, double angle, @NotNull Location point) {
        double radians = Math.toRadians(angle);
        double dx = location.getX() - point.getX();
        double dz = location.getZ() - point.getZ();
        double newX = dx * Math.cos(radians) - dz * Math.sin(radians) + point.getX();
        double newZ = dz * Math.cos(radians) + dx * Math.sin(radians) + point.getZ();
        return new Location(location.getWorld(), newX, location.getY(), newZ);
    }
}
