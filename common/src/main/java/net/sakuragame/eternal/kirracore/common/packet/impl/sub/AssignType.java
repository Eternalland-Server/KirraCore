package net.sakuragame.eternal.kirracore.common.packet.impl.sub;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@SuppressWarnings("SpellCheckingInspection")
public enum AssignType {

    NONE(-1), ASSIGN_WORLD(0), ASSIGN_COORD(1);

    @Getter
    private final int num;

    AssignType(int num) {
        this.num = num;
    }

    @Nullable
    public static AssignType match(int num) {
        return Arrays.stream(values())
                .filter(t -> t.num == num)
                .findAny()
                .orElse(null);
    }
}