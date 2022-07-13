package net.sakuragame.eternal.kirracore.common.packet;

import com.google.gson.JsonObject;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class PacketMatcher {

    static final ArrayList<IPacket> PACKETS = new ArrayList<>();

    static {
        val subTypes = KirraCoreCommon.getREF().getSubTypesOf(IPacket.class);
        subTypes.forEach(clazz -> {
            try {
                val packet = clazz.newInstance();
                PACKETS.add(packet);
            } catch (Exception exception) {
                System.out.println("[KirraCoreCommon] Failed to initialization.");
                exception.printStackTrace();
            }
        });
    }

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
