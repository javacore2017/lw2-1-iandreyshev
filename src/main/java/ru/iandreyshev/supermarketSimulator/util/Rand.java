package ru.iandreyshev.supermarketSimulator.util;

import java.util.*;

public class Rand {
    public static int getInt(int max) {
        return getInt(0, max);
    }

    public static int getInt(int min, int max) {
        if (min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        int bound = (max - min) + 1;
        return getRand().nextInt(bound) + min;
    }

    public static List<Integer> getIntList(int min, int max, int count) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            result.add(getInt(min, max));
        }
        Collections.sort(result);
        return result;
    }

    public static float getFloat(float max) {
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

    private static final int ANTI_DUPLICATE_OFFSET = 773;

    private static int rndOffset = 0;
}
