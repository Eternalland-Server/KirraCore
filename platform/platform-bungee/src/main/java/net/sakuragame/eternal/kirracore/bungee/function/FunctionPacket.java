package net.sakuragame.eternal.kirracore.bungee.function;

import lombok.val;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServerFailed;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketStaffSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.FailedReason;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import net.sakuragame.serversystems.manage.proxy.api.ProxyManagerAPI;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FunctionPacket {

    public static void sendTeleportFailedPacket(@NotNull C2BPacketPlayerSwitchServer c2bPacket) {
        val packet = new B2CPacketPlayerSwitchServerFailed();
        packet.setPlayerIDs(c2bPacket.getPlayerIDs());
        packet.setServerFrom(c2bPacket.getServerFrom());
        packet.setServerTo(c2bPacket.getServerTo());
        packet.setReason(FailedReason.SERVER_CLOSED);
        NetworkHandler.sendPacket(packet, true);
    }

    public static void sendTeleportPacket(@NotNull C2BPacketPlayerSwitchServer c2bPacket, @NotNull ServerInfo server) {
        val packet = new B2CPacketPlayerSwitchServer();
        packet.setPlayerIDs(c2bPacket.getPlayerIDs());
        packet.setServerFrom(c2bPacket.getServerFrom());
        packet.setServerTo(c2bPacket.getServerTo());
        packet.setAssignType(c2bPacket.getAssignType());
        packet.setAssignValue(c2bPacket.getAssignValue());
        NetworkHandler.sendPacket(packet, true);
        KirraCoreBungee.getInstance().getProxy().getScheduler().schedule(KirraCoreBungee.getInstance(), () -> {
            val players = c2bPacket.getPlayerIDs()
                    .stream()
                    .map(ProxyManagerAPI::getUserUUID)
                    .map(KirraCoreBungee.getInstance().getProxy()::getPlayer)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            players.forEach(player -> {
                        if (player.getServer().getInfo().getName().equals(server.getName())) {
                            sendTeleportFailedPacket(c2bPacket);
                            return;
                        }
                        player.connect(server, (bool, throwable) -> {
                            if (!bool) {
                                sendTeleportFailedPacket(c2bPacket);
                            }
                        });
                    }
            );
        }, 1, TimeUnit.SECONDS);
    }

    public static void sendStaffSwitchServerPacket(
            @NotNull String staffName,
            @NotNull String serverID,
            boolean isJoin
    ) {
        val packet = new B2CPacketStaffSwitchServer();
        packet.setStaffName(staffName);
        packet.setJoinOrQuitServerID(serverID);
        packet.setJoin(isJoin);
        NetworkHandler.sendPacket(packet, true);
    }
}