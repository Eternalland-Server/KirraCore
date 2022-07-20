package net.sakuragame.eternal.kirracore.bukkit.compat.mythicmobs.targeter;

import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.placeholders.parsers.PlaceholderInt;
import io.lumine.xikage.mythicmobs.skills.targeters.ILocationSelector;
import io.lumine.xikage.mythicmobs.utils.numbers.RandomInt;
import lombok.val;

import java.util.HashSet;

import static java.lang.Math.random;

@SuppressWarnings({"SpellCheckingInspection"})
public class RandomLocationTargeter extends ILocationSelector {

    private final PlaceholderInt amount;
    private final int maxRadius;
    private final int minRadius;

    public RandomLocationTargeter(MythicLineConfig mlc) {
        super(mlc);
        this.amount = mlc.getPlaceholderInteger(new String[]{"amount", "a"}, 5);
        this.maxRadius = mlc.getInteger("max-radius", 10);
        this.minRadius = mlc.getInteger("min-radius", 2);
    }

    @Override
    public HashSet<AbstractLocation> getLocations(SkillMetadata skillMetadata) {
        val targets = new HashSet<AbstractLocation>();
        val target = skillMetadata.getCaster().getEntity().getTarget();
        if (target == null) {
            return targets;
        }
        targets.add(target.getLocation());
        while (targets.size() < amount.get() - 1) {
            val loc = getLocation(target.getLocation().clone(), new RandomInt(minRadius, maxRadius).get());
            targets.add(loc);
        }
        return targets;
    }

    public static AbstractLocation getLocation(AbstractLocation origin, int radius) {
        val angle = random() * 360;
        return origin.add(Math.cos(angle) * radius, 0, Math.sin(angle) * radius);
    }
}