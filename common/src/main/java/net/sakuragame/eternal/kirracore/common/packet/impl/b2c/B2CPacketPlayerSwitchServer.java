package net.sakuragame.eternal.kirracore.common.packet.impl.b2c;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.util.TypeToken;

import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Data
@NoArgsConstructor
public class B2CPacketPlayerSwitchServer implements IPacket {

    private List<Integer> playerIDs;
    private String serverTo;

    private String assignWorld;
    private String assignCoord;

    @Override
    public MatchType type() {
        return MatchType.B2C;
    }

    @Override
    public int id() {
        return 101;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("packetID", id());
        jsonObj.addProperty("playerIDs", KirraCoreCommon.getGSON().toJson(playerIDs));
        jsonObj.addProperty("serverTo", serverTo);
        jsonObj.addProperty("assignWorld", assignWorld);
        jsonObj.addProperty("assignCoord", assignCoord);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.playerIDs = KirraCoreCommon.getGSON().fromJson(jsonObj.get("playerID").getAsString(), TypeToken.INT_LIST_TYPE);
        this.serverTo = jsonObj.get("serverTo").getAsString();
        this.assignWorld = jsonObj.get("assignWorld").getAsString();
        this.assignCoord = jsonObj.get("assignCoord").getAsString();
    }
}
