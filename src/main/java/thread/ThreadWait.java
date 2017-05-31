package thread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by huishen on 17/2/27.
 */
public class ThreadWait {

    //主线程等待子线程执行完毕
    @Test
    public void test1() throws InterruptedException {
        System.out.println("main thread started!!");
        int threadNumber = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            final int threadID = i;
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("threadID:[%s] finished!!", threadID));
                    // countDownLatch.countDown();
                    countDownLatch.countDown();
                }
            }.start();
        }

        // countDownLatch.await();
        countDownLatch.await();
        System.out.println("main thread finished!!");
    }

    @Test
    public void test2(){
        System.out.println("main thread started!!");
        int threadNumber = 10;
        for (int i = 0; i < threadNumber; i++) {
            final int threadID = i;
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("threadID:[%s] finished!!", threadID));
            });
        }

        while (Thread.activeCount()>1)
            Thread.yield();
        System.out.println("main thread finished!!");
    }
}