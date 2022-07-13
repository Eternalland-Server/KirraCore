package net.sakuragame.eternal.kirracore.common.util;

import lombok.Getter;
import lombok.Setter;

public class Pair<K, V> {

    @Getter
    @Setter
    private K a;
    @Getter
    @Setter
    private V b;

    public static <K, V> Pair<K, V> createPair(K a, V b) {
        return new Pair<>(a, b);
    }

    public Pair(K a, V b) {
        this.a = a;
        this.b = b;
    }
}