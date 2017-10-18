package ru.iandreyshev.util;

import java.util.Calendar;
import java.util.Random;

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
        return getRand().nextInt((max - min) + 1) + min;
    }

    public static int[] getInt(int min, int max, int count) {
        int[] result = new int[count];
        for (int i = 0; i < result.length; ++i) {
            result[i] = getInt(min, max);
        }
        return result;
    }

    public static Random getRand() {
        long currTime = Calendar.getInstance().getTimeInMillis();
        return new Random(currTime);
    }
}
