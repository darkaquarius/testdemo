package util;

import java.util.Random;

/**
 * @author huishen
 * @date 2018-12-14 14:01
 */
public class Utils {
    private static Random random = new Random();

    public static void sleep() {
        sleep(2000);
    }

    public static void randomSleep() {
        // 最多10s
        final int bound = 10000;
        int millis = random.nextInt(bound);
        sleep(millis);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
