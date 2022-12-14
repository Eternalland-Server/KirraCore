package net.sakuragame.eternal.kirracore.bukkit.listener;

import com.sakuragame.eternal.justattribute.api.event.role.RoleLaunchAttackEvent;
import lombok.val;
import lombok.var;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.sakuragame.eternal.kirracore.bukkit.KConfiguration;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KListener;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.TypeUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

@KListener
public class ListenerCommon implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        val player = e.getPlayer();
        player.sendTitle("", "", 0, 1, 0);
        player.resetTitle();
        if (KConfiguration.isTeleportWhenJoin()) {
            val loc = KirraCoreBukkit.getInstance().getCompatManager().getMultiverseCore().getMVWorldManager().getMVWorld("world").getSpawnLocation();
            player.teleport(loc);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        val entity = TypeUtil.safeCast(event.getEntity(), Player.class);
        val attacker = TypeUtil.safeCast(event.getEntity(), Player.class);
        if (entity == null || attacker == null) {
            return;
        }
        if (!KConfiguration.isAllowedPvP()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        val player = e.getPlayer();
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ()) {
            if (player.getGameMode() == GameMode.CREATIVE && player.isFlying()) {
                if (player.isSprinting()) {
                    var flySpeed = (player.getFlySpeed() + 0.005f);
                    if (flySpeed > 1.0f) flySpeed = 1.0f;
                    player.setFlySpeed(flySpeed);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(CC.toColored("&7????????????: &b&l" + flySpeed)).create());
                } else if (player.getFlySpeed() > 0.1f) {
                    player.setFlySpeed(0.1f);
                }
            }
        }
    }

    @EventHandler
    public void onBurn(EntityCombustEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(RoleLaunchAttackEvent.Post event) {
        if (!event.isCancelled()) {
            val player = event.getPlayer();
            val loc = event.getVictim().getLocation().clone().add(0.0, 0.3, 0.0);
            Utils.createSweepAttackParticle(player, loc);
        }
    }
}
