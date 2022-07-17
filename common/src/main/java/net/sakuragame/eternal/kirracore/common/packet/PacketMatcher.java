package net.sakuragame.eternal.kirracore.common.packet;

import com.google.gson.JsonObject;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.packet.impl.a2c.A2CPacketServerShutdown;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServer;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketPlayerSwitchServerFailed;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.B2CPacketStaffJoinOrQuit;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketHeartBeat;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.C2BPacketPlayerSwitchServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PacketMatcher {

    static final ArrayList<IPacket> PACKETS = new ArrayList<IPacket>() {{
        add(new A2CPacketServerShutdown());
        add(new B2CPacketPlayerSwitchServer());
        add(new B2CPacketPlayerSwitchServerFailed());
        add(new B2CPacketStaffJoinOrQuit());
        add(new C2BPacketHeartBeat());
        add(new C2BPacketPlayerSwitchServer());
    }};

    @Nullable
    public static IPacket match(@NotNull JsonObject jsonObj, @NotNull List<MatchType> types) {
        val id = jsonObj.get("packetID").getAsInt();
        val fPacket = PACKETS.stream()
                .filter(p -> p.id() == id)
                .filter(p -> types.contains(p.type()))
                .findFirst()
                .orElse(null);
        if (fPacket == null) {
            return null;
        }
        try {
            val packet = fPacket.getClass().newInstance();
            packet.deserialized(jsonObj);
            return packet;
        } catch (Exception ignored) {
        }
        return null;
    }
}
