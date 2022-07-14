package net.sakuragame.eternal.kirracore.common.packet.impl.c2b;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;
import net.sakuragame.eternal.kirracore.common.util.TypeToken;

import java.util.List;

@SuppressWarnings({"SpellCheckingInspection"})
@Data
@NoArgsConstructor
public class C2BPacketPlayerSwitchServer implements IPacket {

    private List<Integer> playerIDs;
    private String serverFrom;
    private String serverTo;

    private SwitchType switchType;

    private String assignWorld;
    private String assignCoord;

    @Override
    public MatchType type() {
        return MatchType.C2B;
    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("packetID", id());
        jsonObj.addProperty("playerIDs", KirraCoreCommon.getGSON().toJson(playerIDs));
        jsonObj.addProperty("serverFrom", serverFrom);
        jsonObj.addProperty("serverTo", serverTo);
        jsonObj.addProperty("switchType", switchType.getNum());
        jsonObj.addProperty("assignWorld", assignWorld);
        jsonObj.addProperty("assignCoord", assignCoord);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.playerIDs = KirraCoreCommon.getGSON().fromJson(jsonObj.get("playerID").getAsString(), TypeToken.INT_LIST_TYPE);
        jsonObj.addProperty("serverFrom", serverFrom);
        this.serverTo = jsonObj.get("serverTo").getAsString();
        this.switchType = SwitchType.match(jsonObj.get("switchType").getAsInt());
        this.assignWorld = jsonObj.get("assignWorld").getAsString();
        this.assignCoord = jsonObj.get("assignCoord").getAsString();
    }
}