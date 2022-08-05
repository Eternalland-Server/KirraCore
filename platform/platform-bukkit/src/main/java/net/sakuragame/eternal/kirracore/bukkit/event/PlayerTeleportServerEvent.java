package net.sakuragame.eternal.kirracore.bukkit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.FailedReason;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public class PlayerTeleportServerEvent extends KirraEvent {

    @Getter
    @NotNull
    private final Player player;

    @Getter
    @Nullable
    private final FailedReason reason;

    public boolean isFailed() {
        return reason != null;
    }
}