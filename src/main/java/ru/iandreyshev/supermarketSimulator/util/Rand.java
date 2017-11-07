package ru.iandreyshev.supermarketSimulator.util;

import java.util.*;

public class Rand {
    private static final int ANTI_DUPLICATE_OFFSET = 23;

    private static int rndOffset = 0;

    public static int randInt(int max) {
        return randInt(0, max);
    }

    public static int randInt(int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        int bound = (max - min) + 1;
        return getRand().nextInt(bound) + min;
    }

    public static float randFloat(float max) {
        return getRand().nextFloat() * max;
    }

    private static Random getRand() {
        if (Integer.MAX_VALUE - ANTI_DUPLICATE_OFFSET < rndOffset) {
            rndOffset = 0;
        }
        rndOffset += ANTI_DUPLICATE_OFFSET;
        long currTime = Calendar.getInstance().getTimeInMillis();
        return new Random(currTime + rndOffset);
    }
}
