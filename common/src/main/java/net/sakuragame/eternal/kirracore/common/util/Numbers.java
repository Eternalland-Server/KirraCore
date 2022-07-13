package net.sakuragame.eternal.kirracore.common.util;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Numbers {

    private static final DecimalFormat doubleFormat = new DecimalFormat("#.##");

    public static Random getRandom() {
        return ThreadLocalRandom.current();
    }

    public static boolean random(double v) {
        return ThreadLocalRandom.current().nextDouble() <= v;
    }

    public static int random(int v) {
        return ThreadLocalRandom.current().nextInt(v);
    }

    public static Double format(Double num) {
        return Double.valueOf(doubleFormat.format(num));
    }

    public static int getRandomInteger(Number num1, Number num2) {
        int min = Math.min(num1.intValue(), num2.intValue());
        int max = Math.max(num1.intValue(), num2.intValue());
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double getRandomDouble(Number num1, Number num2) {
        double min = Math.min(num1.doubleValue(), num2.doubleValue());
        double max = Math.max(num1.doubleValue(), num2.doubleValue());
        if (min == max) {
            return max;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static Boolean getBoolean(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        char var = str.charAt(0);
        return var == 'y' || var == 'Y' || var == 't' || var == 'T' || var == '1';
    }
}