package net.sakuragame.eternal.kirracore.bukkit.compat.skript;

import ch.njol.skript.Skript;
import net.sakuragame.eternal.kirracore.bukkit.compat.CompatHandler;
import net.sakuragame.eternal.kirracore.bukkit.compat.skript.effects.*;

@SuppressWarnings("SpellCheckingInspection")
public class CompatSkriptHandler implements CompatHandler {

    @Override
    public String getPlugin() {
        return "Skript";
    }

    @Override
    public void init() {
        registerEffects();
    }

    private static void registerEffects() {
        Skript.registerEffect(EffectActionText.class, "send eternal action tip %string% to %player%");
        Skript.registerEffect(EffectPlayAnimation.class, "play animation %string% from uuid %string%");
        Skript.registerEffect(EffectStopAnimation.class, "stop animation %string% from uuid %string%");
        Skript.registerEffect(EffectSetModel.class, "set model of uuid %string% to %string%");
        Skript.registerEffect(EffectSoundPlay.class, "play ogg sound %string% at volume %number% with pitch %number% and loop %boolean% to %player%");
        Skript.registerEffect(EffectSoundStop.class, "stop ogg sound %string% to %player%");
    }
}
