package net.diabolo.diabolomod.entity.custom.topaz_golem;

import java.util.Arrays;
import java.util.Comparator;

public enum TopazGolemVariant {
    BASIC_BASIC(0),
    BLASTER_BASIC(1),
    MINER_BASIC(2),
    BLASTER_SPEED(3),
    MINER_SPEED(4),
    BASIC_SPEED(5);

    private static final TopazGolemVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(TopazGolemVariant::getId)).toArray(TopazGolemVariant[]::new);
    private final int id;

    TopazGolemVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TopazGolemVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static boolean isSpeedVariant(TopazGolemVariant v){
        return (v == TopazGolemVariant.BASIC_SPEED ||
                v == TopazGolemVariant.BLASTER_SPEED ||
                v == TopazGolemVariant.MINER_SPEED);
    }
}
