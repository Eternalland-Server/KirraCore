package net.sakuragame.eternal.kirracore.common.packet.impl.b2c;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub.FailedReason;
import net.sakuragame.eternal.kirracore.common.util.TypeToken;

import java.util.List;

@Data
@NoArgsConstructor
public class B2CPacketPlayerSwitchServerFailed implements IPacket {

    private List<Integer> playerIDs;
    private String serverTo;

    private FailedReason reason;

    @Override
    public MatchType type() {
        return MatchType.B2C;
    }

    @Override
    public int id() {
        return 102;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("playerIDs", KirraCoreCommon.getGSON().toJson(playerIDs));
        jsonObj.addProperty("serverTo", serverTo);
        jsonObj.addProperty("reason", reason.getNum());
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.playerIDs = KirraCoreCommon.getGSON().fromJson(jsonObj.get("playerID").getAsString(), TypeToken.INT_LIST_TYPE);
        this.serverTo = jsonObj.get("serverTo").getAsString();
        this.reason = FailedReason.match(jsonObj.get("type").getAsInt());
    }
}
