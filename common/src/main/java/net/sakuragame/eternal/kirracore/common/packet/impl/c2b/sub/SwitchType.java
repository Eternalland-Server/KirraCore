package net.sakuragame.eternal.kirracore.common.packet.impl.c2b.sub;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum SwitchType {

    DIRECT(1), BY_BALANCING(2);

    @Getter
    private final int num;

    SwitchType(int num) {
        this.num = num;
    }

    @Nullable
    public static SwitchType match(int num) {
        return Arrays.stream(values())
                .filter(type -> type.num == num)
                .findAny()
                .orElse(null);
    }
}