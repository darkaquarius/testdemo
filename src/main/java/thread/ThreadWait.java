package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/2/27.
 * 主线程等待子线程执行完毕的4种方式：
 * <p>
 * 1. Thread.join()
 * 2. ExecutorService    Future
 * 3. countDownLatch.await();
 * 4. Thread.activeCount() > 1  Thread.yield();
 */
@SuppressWarnings("all")
public class ThreadWait {

    /**
     * Thread.join()
     *
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        System.out.println("main thread started!!");
        final int threadNumber = 10;

        Thread thread1 = new Thread(runnable, "Thread1");
        Thread thread2 = new Thread(runnable, "Thread2");
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("main thread finished!!");
    }

    /**
     * ExecutorService    Future
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        long s = System.currentTimeMillis();
        System.out.println("main thread started!!");
        final int threadNumber = 10;
        ExecutorService executorService =
            new ThreadPoolExecutor(5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                runnable -> new Thread(runnable, "MyThread"),
                new ThreadPoolExecutor.AbortPolicy()
            );

        List<Future<?>> list = new ArrayList<>(threadNumber);

        for (int i = 0; i < threadNumber; i++) {
            Future<?> future = executorService.submit(runnable);
            list.add(future);
        }
        executorService.shutdown();

        List ret = new ArrayList();
        for (Future<?> future : list) {
            ret.add(future.get());
        }
        System.out.println("main thread finished!!" + (System.currentTimeMillis() - s));
        System.out.println(ret);
    }

    /**
     * countDownLatch.await();
     */
    @Test
    public void test3() throws InterruptedException {
        System.out.println("main thread started!!");
        final int threadNumber = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber);

        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " started");
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " stop");
                countDownLatch.countDown();
            }, "Thread" + i).start();
        }

        countDownLatch.await();
        System.out.println("main thread finished!!");
    }

    /**
     * Thread.activeCount() > 1
     * Thread.yield不需要被唤醒
     */
    @Test
    public void test4() {
        System.out.println("main thread started!!");
        int threadNumber = 10;

        for (int i = 0; i < threadNumber; i++) {
            new Thread(runnable, "Thread" + i).start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("main thread finished!!");
    }

    private Runnable runnable = () -> {
        System.out.println(Thread.currentThread().getName() + " started");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " stop");
    };

}