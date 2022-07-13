package net.sakuragame.eternal.kirracore.common.util;

import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class TypeToken {

    public static Type INT_LIST_TYPE = new com.google.common.reflect.TypeToken<List<Integer>>() {

    }.getType();
}