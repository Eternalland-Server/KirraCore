package net.sakuragame.eternal.kirracore.bukkit.network.listener;

import lombok.val;
import lombok.var;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.bukkit.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import net.sakuragame.eternal.kirracore.bukkit.util.Broadcast;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketStaffJoinOrQuit;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2c.C2CPacketServerShutdown;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;

public class ListenerPacket {

    @KComingPacketHandler
    public void onStaffJoinOrQuit(B2CPacketStaffJoinOrQuit packet) {
        var message = "";
        if (packet.isJoin()) {
            message = "&c[System] &7管理员 " + packet.getStaffName() + " 进入了服务器.";
        } else {
            message = "&c[System] &7管理员 " + packet.getStaffName() + " 退出了服务器.";
        }
        Broadcast.send(CC.toColored(message), KirraCoreBukkitAPI::isAdminPlayer);
    }

    @KComingPacketHandler
    public void onServerShutdown(C2CPacketServerShutdown packet) {
        val serverID = ClientManagerAPI.getServerID();
        if (!serverID.equals(packet.getServerID())) {
            return;
        }
        FunctionRestart.execute((int) packet.getDelay(), packet.getReason());
    }
}