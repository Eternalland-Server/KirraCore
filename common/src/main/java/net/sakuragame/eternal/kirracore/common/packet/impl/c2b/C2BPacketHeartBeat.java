package net.sakuragame.eternal.kirracore.common.packet.impl.c2b;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.sakuragame.eternal.kirracore.common.packet.IPacket;
import net.sakuragame.eternal.kirracore.common.packet.MatchType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class C2BPacketHeartBeat implements IPacket {

    private String serverID;
    private double tps;
    private long time;

    private List<Integer> onlinePlayers;

    @Override
    public MatchType type() {
        return MatchType.C2B;
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public JsonObject serialized() {
        val jsonObj = new JsonObject();
        jsonObj.addProperty("packetID", id());
        jsonObj.addProperty("serverID", serverID);
        jsonObj.addProperty("tps", tps);
        jsonObj.addProperty("time", time);

        val jsonArray = new JsonArray();
        onlinePlayers.forEach(jsonArray::add);

        jsonObj.add("onlinePlayers", jsonArray);
        return jsonObj;
    }

    @Override
    public void deserialized(JsonObject jsonObj) {
        this.serverID = jsonObj.get("serverID").getAsString();
        this.tps = jsonObj.get("tps").getAsDouble();
        this.time = jsonObj.get("time").getAsLong();

        this.onlinePlayers = new ArrayList<>();
        if (jsonObj.has("onlinePlayers")) {
            jsonObj.get("onlinePlayers").getAsJsonArray().forEach(element -> this.onlinePlayers.add(element.getAsInt()));
        }
    }
}