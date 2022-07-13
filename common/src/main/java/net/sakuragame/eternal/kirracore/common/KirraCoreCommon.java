package net.sakuragame.eternal.kirracore.common;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import lombok.Getter;
import org.reflections.Reflections;

@SuppressWarnings("SpellCheckingInspection")
public class KirraCoreCommon {

    @Getter
    static Gson GSON = new Gson();

    @Getter
    static JsonParser JSON_PARSER = new JsonParser();

    @Getter
    static Reflections REF = new Reflections("net.sakuragame.eternal.kirracore.common");
}