package net.sakuragame.eternal.kirracore.bukkit.network.listener;

import lombok.val;
import lombok.var;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkit;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.bukkit.event.PlayerTeleportServerEvent;
import net.sakuragame.eternal.kirracore.bukkit.function.FunctionRestart;
import net.sakuragame.eternal.kirracore.bukkit.util.Broadcast;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.a2c.A2CPacketServerShutdown;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServerFailed;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketStaffSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.TResult;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
import net.sakuragame.eternal.kirracore.common.util.CC;
import net.sakuragame.eternal.kirracore.common.util.PlaceholderTextUtil;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListenerPacket {

    @KComingPacketHandler
    public void onStaffJoinOrQuit(B2CPacketStaffSwitchServer packet) {
        var message = "";
        val replaceMap = new HashMap<String, String>() {{
            put("$staffName", packet.getStaffName());
            put("$serverId", packet.getJoinOrQuitServerID());
        }};
        if (packet.isJoin()) {
            message = PlaceholderTextUtil.replace("&c[System] &7管理员 $staffName 进入了 $serverId 服务器.", replaceMap);
        } else {
            message = PlaceholderTextUtil.replace("&c[System] &7管理员 $staffName 退出了 $serverId 服务器.", replaceMap);
        }
        Broadcast.send(CC.toColored(message), KirraCoreBukkitAPI::isAdminPlayer);
    }

    @KComingPacketHandler
    public void onTeleport(B2CPacketPlayerSwitchServer packet) {
        if (packet.getServerFrom().equals(Utils.getCURRENT_SERVER_ID())) {
            packet.getPlayerIDs()
                    .stream()
                    .map(uid -> Bukkit.getPlayer(ClientManagerAPI.getUserUUID(uid)))
                    .filter(Objects::nonNull).forEach(player -> {
                        val future = KirraCoreBukkitAPI.getTELEPORTING_MAP().get(player.getUniqueId());
                        if (future == null) {
                            return;
                        }
                        future.complete(TResult.SUCCESS);
                        Bukkit.getPluginManager().callEvent(new PlayerTeleportServerEvent(player, null));
                        KirraCoreBukkitAPI.getTELEPORTING_MAP().remove(player.getUniqueId());
                    });
            return;
        }
        if (packet.getServerTo().equals(Utils.getCURRENT_SERVER_ID()) && packet.getAssignType() != AssignType.NONE) {
            val uuids = packet.getPlayerIDs()
                    .stream()
                    .map(ClientManagerAPI::getUserUUID)
                    .collect(Collectors.toList());
            switch (packet.getAssignType()) {
                case ASSIGN_WORLD:
                    uuids.forEach(uuid -> KirraCoreBukkit.getInstance().getProfileManager().getASSIGN_WORLD().put(uuid, packet.getAssignValue()));
                    return;
                case ASSIGN_COORD:
                    uuids.forEach(uuid -> KirraCoreBukkit.getInstance().getProfileManager().getASSIGN_COORD().put(uuid, packet.getAssignValue()));
                    return;
                default:
            }
        }
    }

    @KComingPacketHandler
    public void onTeleportFailed(B2CPacketPlayerSwitchServerFailed packet) {
        if (packet.getServerFrom().equals(Utils.getCURRENT_SERVER_ID())) {
            packet.getPlayerIDs()
                    .stream()
                    .map(uid -> Bukkit.getPlayer(ClientManagerAPI.getUserUUID(uid)))
                    .filter(Objects::nonNull).forEach(player -> {
                        val future = KirraCoreBukkitAPI.getTELEPORTING_MAP().get(player.getUniqueId());
                        if (future == null) {
                            return;
                        }
                        future.complete(TResult.FAILED);
                        Bukkit.getPluginManager().callEvent(new PlayerTeleportServerEvent(player, packet.getReason()));
                        KirraCoreBukkitAPI.getTELEPORTING_MAP().remove(player.getUniqueId());
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