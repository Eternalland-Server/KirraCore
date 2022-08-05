package net.sakuragame.eternal.kirracore.common.util;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PlaceholderTextUtil {

    public static String replace(String str, Map<String, String> replaceMap) {
        AtomicReference<String> toReplaced = new AtomicReference<>(str);
        replaceMap.forEach((name, value) -> toReplaced.set(toReplaced.get().replace(name, value)));
        return toReplaced.get();
    }

    public static List<String> replace(List<String> strings, Map<String, String> replaceMap) {
        val toReturn = new ArrayList<String>();
        strings.forEach(str -> replaceMap.forEach((name, value) -> toReturn.add(str.replace(name, value))));
        return toReturn;
    }
}