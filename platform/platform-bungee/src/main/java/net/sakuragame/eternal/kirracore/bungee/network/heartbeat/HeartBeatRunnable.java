package net.sakuragame.eternal.kirracore.bungee.network.heartbeat;

import lombok.Getter;
import lombok.val;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketHeartBeat;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HeartBeatRunnable {

    @Getter
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    @Getter
    private static final HashMap<String, C2BPacketHeartBeat> HEART_BEAT_MAP = new HashMap<>();

    public static void run() {
        SCHEDULER.scheduleAtFixedRate(() -> HEART_BEAT_MAP.forEach((serverID, packet) -> {
            if (System.currentTimeMillis() - packet.getTime() > 5_000L) {
                if (KirraCoreBungee.getInstance().getServerManager().getServers().containsKey(packet.getServerID())) {
                    KirraCoreBungee.getInstance().getServerManager().setOffline(packet.getServerID());
                }
            } else {
                if (!KirraCoreBungee.getInstance().getServerManager().getServers().containsKey(packet.getServerID())) {
                    KirraCoreBungee.getInstance().getServerManager().setOnline(packet.getServerID());
                }
            }
        }), 0, 1, TimeUnit.SECONDS);
    }

    public static void update(C2BPacketHeartBeat packet) {
        val serverID = packet.getServerID();
        if (HEART_BEAT_MAP.containsKey(serverID)) {
            HEART_BEAT_MAP.replace(serverID, packet);
            return;
        }
        HEART_BEAT_MAP.put(serverID, packet);
    }
}
