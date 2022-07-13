package net.sakuragame.eternal.kirracore.bukkit.util;

import lombok.Getter;
import lombok.val;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

@SuppressWarnings("SpellCheckingInspection")
public class Utils {

    @Getter
    private static final String ADMIN_PERMISSION = "admin";

    public static boolean isSpawnServer() {
        return ClientManagerAPI.getServerID().contains("rpg-spawn");
    }

    public static void info(String message) {
        Bukkit.getLogger().info("[KirraCoreBukkit] " + message);
    }

    public static void createSweepAttackParticle(Player player, Location loc) {
        val i = 1;
        val o = new int[]{90, 180, 260};
        for (int oa : o) {
            double x = i * Math.cos(oa / 180.0D * Math.PI) * -Math.cos((double) ((90.0F - loc.clone().getPitch() + 90.0F) / 180.0F) * Math.PI) * Math.sin((double) (loc.clone().getYaw() / 180.0F) * Math.PI) + i * Math.sin(oa / 180.0D * Math.PI) * -Math.sin((double) ((loc.clone().getYaw() - 90.0F) / 180.0F) * Math.PI);
            double y = 0.8D + i * Math.cos(oa / 180.0D * Math.PI) * Math.sin((double) ((90.0F - loc.clone().getPitch() + 90.0F) / 180.0F) * Math.PI);
            double z = i * (Math.cos(oa / 180.0D * Math.PI) * Math.cos((double) ((90.0F - loc.clone().getPitch() + 90.0F) / 180.0F) * Math.PI) * Math.cos((double) (loc.clone().getYaw() / 180.0F) * Math.PI) + Math.sin(oa / 180.0D * Math.PI) * Math.cos((double) ((loc.clone().getYaw() - 90.0F) / 180.0F) * Math.PI));
            val offset = loc.clone().add(x, y, z);
            player.spawnParticle(Particle.SWEEP_ATTACK, offset, 1);
        }
    }

    public static boolean hasAdminPermission(Player player) {
        return player.hasPermission(ADMIN_PERMISSION);
    }

    public static String getLoadingSymbolByNumber(int index) {
        switch (index) {
            case 1: {
                return "/";
            }
            case 2: {
                return "-";
            }
            case 3: {
                return "\\";
            }
            default: {
                return "|";
            }
        }
    }
}