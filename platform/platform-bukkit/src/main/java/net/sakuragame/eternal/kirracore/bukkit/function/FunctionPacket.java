package net.sakuragame.eternal.kirracore.bukkit.function;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.KirraCoreBukkitAPI;
import net.sakuragame.eternal.kirracore.bukkit.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.bukkit.util.Utils;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.TResult;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FunctionPacket {

    public static void sendC2BPacket(
            @NotNull String serverID,
            @NotNull UUID[] uuids,
            @Nullable AssignType assignType,
            @Nullable String assignValue,
            @NotNull SwitchType switchType
    ) {
        val packet = new C2BPacketPlayerSwitchServer();
        packet.setPlayerIDs(
                Arrays.stream(uuids)
                        .map(ClientManagerAPI::getUserID)
                        .filter(uid -> uid != -1)
                        .collect(Collectors.toList())
        );
        packet.setServerFrom(Utils.getCURRENT_SERVER_NAME());
        packet.setServerTo(serverID);
        packet.setAssignType(assignType == null ? AssignType.NONE : assignType);
        packet.setAssignValue(assignValue == null ? "null" : assignValue);
        packet.setSwitchType(switchType);
        NetworkHandler.sendPacket(packet, true);
    }

    public static void handleC2BFuture(@NotNull UUID[] uuids, @NotNull CompletableFuture<TResult> future) {
        Arrays.stream(uuids)
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    KirraCoreBukkitAPI.getTELEPORTING_MAP().put(player.getUniqueId(), future);
                    KirraCoreBukkitAPI.showDefaultLoadingAnimation(player);
                });
    }
}