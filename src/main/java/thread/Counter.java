package thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by huishen on 17/2/27.
 *
 */
public class Counter {

    private static int count = 0;

    public static void inc() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;
    }

    @Test
    public void test1() {
        final CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                Counter.inc();
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.count);
    }

}
