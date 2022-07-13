package net.sakuragame.eternal.kirracore.common.util;

@SuppressWarnings("unchecked")
public class ClassUtil {

    public static <T> T safeCast(Object obj, Class<T> type) {
        if (obj == null) {
            return null;
        }
        return type.isInstance(obj) ? (T) obj : null;
    }
}