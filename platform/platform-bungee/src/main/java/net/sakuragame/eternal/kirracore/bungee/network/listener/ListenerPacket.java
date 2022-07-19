package net.sakuragame.eternal.kirracore.bungee.network.listener;

import lombok.val;
import net.md_5.bungee.api.config.ServerInfo;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.function.FunctionPacket;
import net.sakuragame.eternal.kirracore.bungee.network.heartbeat.HeartBeatRunnable;
import net.sakuragame.eternal.kirracore.common.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketHeartBeat;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;

public class ListenerPacket {

    @KComingPacketHandler
    public void onHeartBeat(C2BPacketHeartBeat packet) {
        HeartBeatRunnable.update(packet);
    }

    @KComingPacketHandler
    public void onPlayerSwitchServer(C2BPacketPlayerSwitchServer packet) {
        ServerInfo server;
        val type = packet.getSwitchType();
        if (type == SwitchType.DIRECT) {
            server = KirraCoreBungee.getInstance().getServerManager().getFirstOrNull(packet.getServerTo());
        } else {
            server = KirraCoreBungee.getInstance().getServerManager().getByBalancing(packet.getServerTo());
        }
        if (server == null || server.getName().equalsIgnoreCase(packet.getServerFrom())) {
            FunctionPacket.sendTeleportFailedPacket(packet);
            return;
        }
        FunctionPacket.sendTeleportPacket(packet, server);
    }
}