package net.sakuragame.eternal.kirracore.bungee.function;

import lombok.val;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServerFailed;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.FailedReason;
import net.sakuragame.serversystems.manage.proxy.api.ProxyManagerAPI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class FunctionTeleport {

    public static void sendTeleportFailedPacket(@NotNull List<Integer> playerIDs, @NotNull String serverID) {
        val packet = new B2CPacketPlayerSwitchServerFailed();
        packet.setPlayerIDs(playerIDs);
        packet.setServerTo(serverID);
        packet.setReason(FailedReason.SERVER_CLOSED);
        NetworkHandler.sendPacket(packet, true);
    }

    public static void doTeleport(
            @NotNull List<Integer> playerIDs,
            @NotNull String serverID,
            @NotNull ServerInfo serverTo,
            @Nullable String assignWorld,
            @Nullable String assignCoord
    ) {
        val packet = new B2CPacketPlayerSwitchServer();
        packet.setPlayerIDs(playerIDs);
        packet.setServerTo(serverID);
        packet.setAssignWorld(assignWorld);
        packet.setAssignCoord(assignCoord);
        NetworkHandler.sendPacket(packet, true);
        KirraCoreBungee.getInstance().getProxy().getScheduler().schedule(KirraCoreBungee.getInstance(), () -> {
            val players = playerIDs
                    .stream()
                    .map(ProxyManagerAPI::getUserUUID)
                    .map(KirraCoreBungee.getInstance().getProxy()::getPlayer)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            players.forEach(player -> player.connect(serverTo));
        }, 1, TimeUnit.SECONDS);
    }
}