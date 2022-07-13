package net.sakuragame.eternal.kirracore.bukkit.network.heartbeat;

import lombok.val;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.sakuragame.eternal.kirracore.bukkit.network.NetworkHandler;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketHeartBeat;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.Bukkit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class HeartBeatRunnable {

    private static final String SERVER_ID = ClientManagerAPI.getServerID();
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    public static void run() {
        SCHEDULER.scheduleAtFixedRate(() -> {
            val playerIDs = Bukkit.getOnlinePlayers()
                    .stream()
                    .map(player -> ClientManagerAPI.getUserID(player.getUniqueId()))
                    .collect(Collectors.toList());

            val packet = new C2BPacketHeartBeat();

            packet.setServerID(SERVER_ID);
            packet.setTps(getRecentTps());
            packet.setTime(System.currentTimeMillis());
            packet.setOnlinePlayers(playerIDs);

            NetworkHandler.sendPacket(packet, true);
        }, 0, 1, TimeUnit.SECONDS);
    }

    private static double getRecentTps() {
        return MinecraftServer.getServer().recentTps[0];
    }
}
