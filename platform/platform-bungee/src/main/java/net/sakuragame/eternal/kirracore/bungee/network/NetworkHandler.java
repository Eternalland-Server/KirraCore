package net.sakuragame.eternal.kirracore.bungee.network;

import lombok.val;
import net.sakuragame.eternal.kirracore.bungee.KirraCoreBungee;
import net.sakuragame.eternal.kirracore.bungee.network.heartbeat.HeartBeatRunnable;
import net.sakuragame.eternal.kirracore.bungee.network.listener.ListenerPacket;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.packet.PacketListenerData;
import net.sakuragame.eternal.kirracore.common.packet.PacketMatcher;
import net.sakuragame.eternal.kirracore.common.packet.function.FunctionPacketRegister;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import net.sakuragame.serversystems.manage.api.redis.RedisMessageListener;
import net.sakuragame.serversystems.manage.proxy.api.ProxyManagerAPI;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class NetworkHandler {

    static final List<PacketListenerData> PACKET_LISTENERS = new ArrayList<>();

    public static void init() {
        ProxyManagerAPI.getRedisManager().subscribe("KirraCore");
        ProxyManagerAPI.getRedisManager().registerListener(new PacketListener());

        FunctionPacketRegister.registerListener(new ListenerPacket(), PACKET_LISTENERS);

        HeartBeatRunnable.run();
    }


    public static void sendPacket(IPacket packet, boolean async) {
        val serialized = packet.serialized();
        val str = serialized.toString();
        if (async) {
            ProxyManagerAPI.getRedisManager().publishAsync("KirraCore", "main", str);
            return;
        }
        ProxyManagerAPI.getRedisManager().publish("KirraCore", "main", str);
    }

    private static class PacketListener extends RedisMessageListener {

        public PacketListener() {
            super(false, "KirraCore");
        }

        @Override
        public void onMessage(String serviceName, String sourceServer, String channel, String[] messages) {
            KirraCoreBungee.getInstance().getProxy().getScheduler().runAsync(KirraCoreBungee.getInstance(), () -> {
                if (messages.length < 1) {
                    return;
                }
                val jsonObj = KirraCoreCommon.getJSON_PARSER().parse(messages[0]).getAsJsonObject();
                val packet = PacketMatcher.match(jsonObj, new ArrayList<MatchType>() {{
                    add(MatchType.C2B);
                }});
                if (packet == null) {
                    return;
                }
                packet.deserialized(jsonObj);
                if (packet instanceof C2BPacketPlayerSwitchServer) {
                    System.out.println("reached switch");
                }
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
