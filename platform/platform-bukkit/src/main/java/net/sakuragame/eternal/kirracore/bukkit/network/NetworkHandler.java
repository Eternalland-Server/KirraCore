package net.sakuragame.eternal.kirracore.bukkit.network;

import lombok.val;
import net.sakuragame.eternal.kirracore.bukkit.annotation.KComingPacketHandler;
import net.sakuragame.eternal.kirracore.bukkit.network.heartbeat.HeartBeatRunnable;
import net.sakuragame.eternal.kirracore.bukkit.util.Scheduler;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.packet.PacketMatcher;
import net.sakuragame.serversystems.manage.api.redis.RedisMessageListener;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "rawtypes"})
public class NetworkHandler {

    static final List<PacketListenerData> PACKET_LISTENERS = new ArrayList<>();

    public static void init() {
        ClientManagerAPI.getRedisManager().subscribe("KirraCore");
        ClientManagerAPI.getRedisManager().registerListener(new PacketListener());

        HeartBeatRunnable.run();
    }

    public static void registerListener(Object clazz) {
        for (Method method : clazz.getClass().getDeclaredMethods()) {
            if (method.getDeclaredAnnotation(KComingPacketHandler.class) != null) {
                Class packetClass = null;
                if (method.getParameters().length > 0) {
                    if (IPacket.class.isAssignableFrom(method.getParameters()[0].getType())) {
                        packetClass = method.getParameters()[0].getType();
                    }
                }
                if (packetClass != null) {
                    PACKET_LISTENERS.add(new PacketListenerData(clazz, method, packetClass));
                }
            }
        }
    }

    public static void sendPacket(IPacket packet, boolean async) {
        val serialized = packet.serialized();
        val str = serialized.toString();
        if (async) {
            ClientManagerAPI.getRedisManager().publishAsync("KirraCore", "main", str);
            return;
        }
        ClientManagerAPI.getRedisManager().publish("KirraCore", "main", str);
    }

    private static class PacketListener extends RedisMessageListener {

        public PacketListener() {
            super(false, "KirraCore");
        }

        @Override
        public void onMessage(String serviceName, String sourceServer, String channel, String[] messages) {
            Scheduler.runAsync(() -> {
                if (messages.length < 1) {
                    return;
                }
                val jsonObj = KirraCoreCommon.getJSON_PARSER().parse(messages[0]).getAsJsonObject();
                val packet = PacketMatcher.match(jsonObj, new ArrayList<MatchType>() {{
                    add(MatchType.B2C);
                    add(MatchType.C2C);
                }});
                if (packet == null) {
                    return;
                }
                packet.deserialized(jsonObj);
                PACKET_LISTENERS.forEach(listener -> {
                    if (listener.matches(packet)) {
                        try {
                            listener.getMethod().invoke(listener.getInstance(), packet);
                        } catch (Exception exception) {
                            System.out.println("[NetworkHandler] Failed to handle message");
                            exception.printStackTrace();
                        }
                    }
                });
            });
        }
    }
}