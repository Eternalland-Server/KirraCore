package net.sakuragame.eternal.kirracore.bukkit.listener.profile;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.profile.Profile;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

@SuppressWarnings("SpellCheckingInspection")
@KListener
public class ListenerProfile implements Listener {

    static {
        Scheduler.runRepeatAsync(() -> {
            for (Profile profile : KirraCoreBukkit.getInstance().getProfileManager().getPROFILES().values()) {
                profile.onlineMinutes++;
                if (profile.onlineMinutes >= 1 && Utils.isSpawnServer()) {
                    profile.convertMinutes--;
                    if (profile.convertMinutes <= 0) {
                        profile.setConvertModel(null);
                        profile.convertMinutes = 0;
                        profile.loadConvertData();
                        return;
                    }
                }
            }
        }, 0, 20);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        KirraCoreBukkit.getInstance().getProfileManager().read(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent e) {
        val player = e.getPlayer();
        val assignWorld = KirraCoreBukkit.getInstance().getProfileManager().getAssignWorld(player);
        val assignCoord = KirraCoreBukkit.getInstance().getProfileManager().getAssignCoord(player);
        if (assignWorld != null) {
            e.setTo(assignWorld);
            return;
        }
        if (assignCoord != null) {
            e.setTo(assignCoord.getLoc());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        KirraCoreBukkit.getInstance().getProfileManager().save(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        KirraCoreBukkit.getInstance().getProfileManager().save(event.getPlayer());
    }
}