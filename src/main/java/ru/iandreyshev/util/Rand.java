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

    private static Random getRand() {
        long currTime = Calendar.getInstance().getTimeInMillis();
        return new Random(currTime);
    }
}
