package net.sakuragame.eternal.kirracore.common.packet.impl.b2c.sub;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum FailedReason {

    SERVER_CLOSED(0), WHITELIST_ON(1), UNKNOWN(2);

    @Getter
    private final int num;

    FailedReason(int num) {
        this.num = num;
    }

    @Nullable
    public static FailedReason match(int num) {
        return Arrays.stream(values())
                .filter(type -> type.num == num)
                .findAny()
                .orElse(null);
    }
}
