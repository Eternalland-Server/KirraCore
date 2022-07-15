package net.sakuragame.eternal.kirracore.bukkit.event;

import org.bukkit.entity.Player;

public class TeleportServerFailedEvent extends KirraEvent {

    public TeleportServerFailedEvent(Player player) {
        super(player);
    }
}
