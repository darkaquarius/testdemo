package thread;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huishen on 17/7/14.
 */
public class CountDownLatchDemo {

    private static int count;

    public static final int THREAD_NUM = 1000;

    /**
     * CountDownLatch的使用:
     * 1. 在主线程（等待线程）中初始化CountDownLatch：CountDownLatch countDownLatch = new CountDownLatch(n)
     * 2. 在创建的线程（被等待线程）中调用：countDownLatch.countDown();
     * 3. 在主线程（等待线程）中调用：countDownLatch.await();
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    /**
     * 非公平锁
     */
    private static Lock lock = new ReentrantLock();

    @Before
    public void init() {
        count = 0;
        countDownLatch = new CountDownLatch(1000);
    }

    private static void inc1() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;
    }

    private static void inc2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (CountDownLatchDemo.class) {
            count++;
        }
    }

    private static void inc3() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        count++;
        lock.unlock();
    }

    /**
     * count++不是原子操作，所以每次执行，count的值都不同，一般都会小于1000
     */
    @Test
    public void test1() {
        test(() -> {
            CountDownLatchDemo.inc1();
            countDownLatch.countDown();
        });
    }

    /**
     * 使用synchronized把 "count++" 操作变成了同步操作，保证了原子性，所以每次count的结果都是1000，当然执行速度会变慢。
     */
    @Test
    public void test2() {
        test(() -> {
            CountDownLatchDemo.inc2();
            countDownLatch.countDown();
        });
    }

    /**
     * 使用Lock加锁，保证原子性
     */
    @Test
    public void test3() {
        test(() -> {
            CountDownLatchDemo.inc3();
            countDownLatch.countDown();
        });
    }

    /**
     * AtomicInteger的操作是原子性的，每次count的结果都是1000
     * @throws InterruptedException
     */
    @SuppressWarnings("all")
    @Test
    public void  test4() throws InterruptedException {
        AtomicInteger atomicCount = new AtomicInteger(0);

        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                atomicCount.incrementAndGet();
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("运行结果:Counter.count=" + atomicCount);
    }

    /**
     * note1:
     * for循环中创建的线程数要与 new CountDownLatch(n) 中的"n" 相同。
     * <p>
     * <p>
     * note2:
     * 这里循环的次数大于2000就会报错：java.lang.OutOfMemoryError: unable to create new native thread
     *
     */
    public void test(Runnable runnable) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(runnable).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + CountDownLatchDemo.count);
        System.out.println("spend: " + (System.currentTimeMillis() - start));
    }

}
