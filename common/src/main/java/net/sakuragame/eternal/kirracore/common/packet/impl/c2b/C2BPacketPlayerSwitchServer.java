package net.sakuragame.eternal.kirracore.common.packet.impl.c2b;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.KirraCoreCommon;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub.SwitchType;
import net.sakuragame.eternal.kirracore.common.packet.impl.sub.AssignType;
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

    private AssignType assignType;
    private String assignValue;

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
        jsonObj.addProperty("assignType", assignType.getNum());
        jsonObj.addProperty("assignValue", assignValue);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.playerIDs = KirraCoreCommon.getGSON().fromJson(jsonObj.get("playerID").getAsString(), TypeToken.INT_LIST_TYPE);
        this.serverFrom = jsonObj.get("serverFrom").getAsString();
        this.serverTo = jsonObj.get("serverTo").getAsString();
        this.switchType = SwitchType.match(jsonObj.get("switchType").getAsInt());
        this.assignType = AssignType.match(jsonObj.get("assignType").getAsInt());
        this.assignValue = jsonObj.get("assignValue").getAsString();
    }
}