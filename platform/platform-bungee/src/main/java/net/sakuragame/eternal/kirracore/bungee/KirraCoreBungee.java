package net.sakuragame.eternal.kirracore.bungee;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreBungee extends Plugin {

    @Getter
    private static KirraCoreBungee instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
