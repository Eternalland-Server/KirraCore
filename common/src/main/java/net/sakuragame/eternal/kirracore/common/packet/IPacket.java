package net.sakuragame.eternal.kirracore.common.packet;

import com.google.gson.JsonObject;

public interface IPacket {

    MatchType type();

    int id();

    JsonObject serialized();

    void deserialized(JsonObject jsonObj);
}