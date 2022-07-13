package net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.mechanic;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import lombok.val;
import net.sakuragame.eternal.dragoncore.network.PacketSender;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.UUID;

public class TurnToMonsterMechanic extends SkillMechanic implements ITargetedEntitySkill {

    public static String TURN_TO_MONSTER_META_ID = "TurnToMonster";

    protected long duration;
    protected double radius;
    protected double limitDamage;
    protected String modelName;

    public TurnToMonsterMechanic(MythicLineConfig conf) {
        super(conf.getLine(), conf);
        duration = conf.getLong(new String[]{"duration", "d"});
        radius = conf.getDouble(new String[]{"radius", "r"});
        limitDamage = conf.getDouble(new String[]{"limit-damage", "ld"});
        modelName = conf.getString(new String[]{"model-name", "mn"});
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity abstractEntity) {
        if (duration == 0 || limitDamage == 0 || modelName == null) {
            return false;
        }
        val caster = data.getCaster().getEntity().getBukkitEntity();
        if (caster == null) {
            return false;
        }
        val entities = caster.getLocation().getWorld().getNearbyEntities(caster.getLocation(), radius, radius, radius);
        if (entities.isEmpty()) {
            return false;
        }
        val turnedList = new ArrayList<UUID>();
        for (Entity entity : entities) {
            if (!(entity instanceof Player)) {
                continue;
            }
            val player = (Player) entity;
            if (player.hasMetadata(TURN_TO_MONSTER_META_ID)) {
                continue;
            }
            player.setMetadata(TURN_TO_MONSTER_META_ID, new FixedMetadataValue(KirraCoreBukkit.getInstance(), limitDamage));
            turnedList.add(player.getUniqueId());
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> PacketSender.setEntityModel(onlinePlayer, player.getUniqueId(), modelName));
        }
        if (entities.isEmpty()) {
            return false;
        }
        Scheduler.runLater(() -> {
            for (UUID playerUUID : turnedList) {
                Player player = Bukkit.getPlayer(playerUUID);
                if (player == null || !player.isOnline()) {
                    continue;
                }
                player.removeMetadata("TurnToMonster", KirraCoreBukkit.getInstance());
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> PacketSender.setEntityModel(onlinePlayer, player.getUniqueId(), null));
            }
        }, (int) duration);
        return true;
    }
}