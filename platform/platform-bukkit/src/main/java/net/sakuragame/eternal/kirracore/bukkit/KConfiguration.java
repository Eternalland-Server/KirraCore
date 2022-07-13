package net.sakuragame.eternal.kirracore.bukkit;

import lombok.Getter;
import net.sakuragame.eternal.kirracore.common.util.Lazy;

public class KConfiguration {

    @Getter
    Lazy<Boolean> TELEPORT_WHEN_JOIN = new Lazy<>(KirraCoreBukkit.getInstance().getConfig().getBoolean("settings.teleport-when-join"));

    @Getter
    Lazy<Boolean> ALLOWED_PVP = new Lazy<>(KirraCoreBukkit.getInstance().getConfig().getBoolean("settings.allowed-pvp"));
}