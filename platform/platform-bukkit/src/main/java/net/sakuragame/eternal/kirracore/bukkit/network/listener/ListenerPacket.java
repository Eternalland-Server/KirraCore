package net.sakuragame.eternal.kirracore.bukkit.network.listener;

import lombok.val;
import lombok.var;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.bukkit.event.TeleportServerFailedEvent;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import net.sakuragame.eternal.kirracore.bukkit.util.Broadcast;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.a2c.A2CPacketServerShutdown;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServerFailed;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketStaffSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.TResult;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.stream.Collectors;

public class ListenerPacket {

    @KComingPacketHandler
    public void onStaffJoinOrQuit(B2CPacketStaffSwitchServer packet) {
        var message = "";
        if (packet.isJoin()) {
            message = "&c[System] &7管理员 " + packet.getStaffName() + " 进入了 " + packet.getJoinOrQuitServerID() + " 服务器.";
        } else {
            message = "&c[System] &7管理员 " + packet.getStaffName() + " 退出了 " + packet.getJoinOrQuitServerID() + " 服务器.";
        }
        Broadcast.send(CC.toColored(message), KirraCoreBukkitAPI::isAdminPlayer);
    }

    @KComingPacketHandler
    public void onTeleport(B2CPacketPlayerSwitchServer packet) {
        if (packet.getServerFrom().equals(Utils.getCURRENT_SERVER_NAME())) {
            packet.getPlayerIDs()
                    .stream()
                    .map(uid -> Bukkit.getPlayer(ClientManagerAPI.getUserUUID(uid)))
                    .filter(Objects::nonNull).forEach(player -> {
                        val future = KirraCoreBukkitAPI.getTELEPORTING_MAP().get(player.getUniqueId());
                        if (future == null) {
                            return;
                        }
                        future.complete(TResult.SUCCESS);
                        KirraCoreBukkitAPI.getTELEPORTING_MAP().remove(player.getUniqueId());
                    });
            return;
        }
        if (packet.getServerTo().equals(Utils.getCURRENT_SERVER_NAME()) && packet.getAssignType() != AssignType.NONE) {
            val uuids = packet.getPlayerIDs()
                    .stream()
                    .map(ClientManagerAPI::getUserUUID)
                    .collect(Collectors.toList());
            if (packet.getAssignType() == AssignType.ASSIGN_WORLD) {
                uuids.forEach(uuid -> KirraCoreBukkit.getInstance().getProfileManager().getASSIGN_WORLD().put(uuid, packet.getAssignValue()));
            }
            if (packet.getAssignType() == AssignType.ASSIGN_COORD) {
                uuids.forEach(uuid -> KirraCoreBukkit.getInstance().getProfileManager().getASSIGN_COORD().put(uuid, packet.getAssignValue()));
            }
        }
    }

    @KComingPacketHandler
    public void onTeleportFailed(B2CPacketPlayerSwitchServerFailed packet) {
        System.out.println(packet);
        System.out.println(Utils.getCURRENT_SERVER_NAME());
        if (packet.getServerFrom().equals(Utils.getCURRENT_SERVER_NAME())) {
            packet.getPlayerIDs()
                    .stream()
                    .map(uid -> Bukkit.getPlayer(ClientManagerAPI.getUserUUID(uid)))
                    .filter(Objects::nonNull).forEach(player -> {
                        Bukkit.broadcastMessage("reached 1");
                        Bukkit.broadcastMessage(KirraCoreBukkitAPI.getTELEPORTING_MAP().toString());
                        Scheduler.run(() -> {
                            Bukkit.broadcastMessage("run in sync");
                            Bukkit.broadcastMessage(KirraCoreBukkitAPI.getTELEPORTING_MAP().toString());
                        });
                        val future = KirraCoreBukkitAPI.getTELEPORTING_MAP().get(player.getUniqueId());
                        Bukkit.broadcastMessage("reached 2");
                        if (future == null) {
                            return;
                        }
                        Bukkit.broadcastMessage("reached 3");
                        future.complete(TResult.SERVER_CLOSED);
                        KirraCoreBukkitAPI.getTELEPORTING_MAP().remove(player.getUniqueId());
                        new TeleportServerFailedEvent(player).call();
                        Bukkit.broadcastMessage("reached 4");
                    });
        }
    }

    @KComingPacketHandler
    public void onServerShutdown(A2CPacketServerShutdown packet) {
        val serverID = ClientManagerAPI.getServerID();
        if (!serverID.equals(packet.getServerID())) {
            return;
        }
        FunctionRestart.execute((int) packet.getDelay(), packet.getReason());
    }
}