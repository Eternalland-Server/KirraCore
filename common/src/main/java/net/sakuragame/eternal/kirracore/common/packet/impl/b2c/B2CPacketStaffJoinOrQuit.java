package net.sakuragame.eternal.kirracore.common.packet.impl.b2c;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;

@Data
@NoArgsConstructor
public class B2CPacketStaffJoinOrQuit implements IPacket {

    private String staffName;
    private boolean isJoin;

    @Override
    public MatchType type() {
        return MatchType.B2C;
    }

    @Override
    public int id() {
        return 100;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("packetID", id());
        jsonObj.addProperty("staffName", staffName);
        jsonObj.addProperty("isJoin", isJoin);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.staffName = jsonObj.get("staffName").getAsString();
        this.isJoin = jsonObj.get("isJoin").getAsBoolean();
    }
}