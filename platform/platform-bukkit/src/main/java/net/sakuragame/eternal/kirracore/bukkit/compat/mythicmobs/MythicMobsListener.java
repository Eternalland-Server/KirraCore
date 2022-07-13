package net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs;

import com.sakuragame.eternal.justattribute.api.event.role.RoleLaunchAttackEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicTargeterLoadEvent;
import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.mechanic.AttributeDamageMechanic;
import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.mechanic.TurnToMonsterMechanic;
import net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.targeter.RandomLocationTargeter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("SpellCheckingInspection")
public class MythicMobsListener implements Listener {

    @EventHandler
    public void onMechanicLoad(MythicMechanicLoadEvent event) {
        val name = event.getMechanicName();
        val config = event.getConfig();
        if (name.equalsIgnoreCase("AttributeDamage")) {
            event.register(new AttributeDamageMechanic(config.getLine(), config));
        }
        if (name.equalsIgnoreCase("TurnToMonster")) {
            event.register(new TurnToMonsterMechanic(config));
        }
    }

    @EventHandler
    public void onTargeterLoad(MythicTargeterLoadEvent event) {
        if (event.getTargeterName().equalsIgnoreCase("RandomLocationTargeter")) {
            event.register(new RandomLocationTargeter(event.getConfig()));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        val player = e.getPlayer();
        if (player.hasMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID)) {
            player.removeMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID, KirraCoreBukkit.getInstance());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        val player = e.getPlayer();
        if (player.hasMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID)) {
            player.removeMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID, KirraCoreBukkit.getInstance());
        }
    }

    @EventHandler
    public void onTurnToMonsterDamageEvent(RoleLaunchAttackEvent.Pre e) {
        val player = e.getPlayer();
        if (!player.hasMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID)) {
            return;
        }
        double damage = player.getMetadata(TurnToMonsterMechanic.TURN_TO_MONSTER_META_ID).get(0).asDouble();
        e.setDamage(damage);
    }
}