package net.sakuragame.eternal.kirracore.common.packet.impl.a0c;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;

@Data
@NoArgsConstructor
public class A2CPacketServerShutdown implements IPacket {

    private String serverID;
    private long delay;
    private String reason;

    @Override
    public MatchType type() {
        return MatchType.C2C;
    }

    @Override
    public int id() {
        return 200;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("packetID", id());
        jsonObj.addProperty("serverID", serverID);
        jsonObj.addProperty("delay", delay);
        jsonObj.addProperty("reason", reason);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.serverID = jsonObj.get("serverID").getAsString();
        this.delay = jsonObj.get("delay").getAsLong();
        this.reason = jsonObj.get("reason").getAsString();
    }
}