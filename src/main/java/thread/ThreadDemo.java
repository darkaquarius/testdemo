package thread;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huishen on 17/11/3.
 *
 */

public class ThreadDemo {

    private static final int THREAD_NUM = 2100;

    private static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    /**
     * -Xss1024K，只能建2000个线程
     * -Xss256K，还是2000是什么情况？？？
     */
    @SuppressWarnings("all")
    @Test
    public void test1() throws InterruptedException, IOException {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("test end...");
    }

}


