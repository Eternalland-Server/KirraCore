package net.sakuragame.eternal.kirracore.bukkit.compat;

import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;

public interface CompatHandler {

    default KirraCoreBukkit getInstance() {
        return KirraCoreBukkit.getInstance();
    }

    String getPlugin();

    void init();
}
