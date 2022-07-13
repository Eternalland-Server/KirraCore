package net.sakuragame.eternal.kirracore.bukkit.profile.listener;

import me.skymc.kirracoord.Coord;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.annotation.KListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        KirraCoreBukkit.getInstance().getProfileManager().read(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        Location assignWorld = KirraCoreBukkit.getInstance().getProfileManager().getAssignWorld(player);
        Coord assignCoord = KirraCoreBukkit.getInstance().getProfileManager().getAssignCoord(player);
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
