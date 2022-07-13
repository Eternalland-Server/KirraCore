package net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.mechanic;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.ITargetedEntitySkill;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.damage.DamagingMechanic;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderDouble;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class AttributeDamageMechanic extends DamagingMechanic implements ITargetedEntitySkill {

    protected PlaceholderDouble num;

    public AttributeDamageMechanic(String line, MythicLineConfig mlc) {
        super(line, mlc);
        this.num = PlaceholderDouble.of(mlc.getString(new String[]{"amount", "a"}, "1"));
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        val caster = data.getCaster();
        if (!target.isDead() && !caster.isUsingDamageSkill() && (!target.isLiving() || !(target.getHealth() <= 0.0D))) {
            val event = new EntityDamageByEntityEvent(caster.getEntity().getBukkitEntity(), target.getBukkitEntity(), EntityDamageEvent.DamageCause.CUSTOM, num.get());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return false;
            }
            doDamage(caster, target, event.getFinalDamage());
            return true;
        }
        return false;
    }
}
