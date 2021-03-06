package thread.atomic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author huishen
 * @date 2019-01-18 14:59
 * <p>
 * JDK 8新特性LongAdder和AtomicLong的性能测试对比
 */
public class AtomicLongAndLongAdder {


    /**
     * 在线程数量比较多，单次运行时间较长的情况下，此时线程间竞争更为激烈，重试次数较多，LongAddr的性能更好
     * 在线程数量比较少，单次运行时间较短的情况下，此时线程间竞争没有太激烈，重试次数较少，AtomicLong的性能更好
     */
    @Test
    public void test1() {
        //testAtomicLongVSLongAdder(10, 10000);
        //testAtomicLongVSLongAdder(40, 200000);
        testAtomicLongVSLongAdder(5, 20000000);
    }

    static void testAtomicLongVSLongAdder(final int threadCount, final int times){
        try {
            long start = System.currentTimeMillis();
            testLongAdder(threadCount, times);
            long end = System.currentTimeMillis() - start;
            System.out.println("条件>>>>>>线程数:" + threadCount + ", 单线程操作计数" + times);
            System.out.println("结果>>>>>>LongAdder方式增加计数" + (threadCount * times) + "万次,共计耗时:" + end);

            long start2 = System.currentTimeMillis();
            testAtomicLong(threadCount, times);
            long end2 = System.currentTimeMillis() - start2;
            System.out.println("条件>>>>>>线程数:" + threadCount + ", 单线程操作计数" + times);
            System.out.println("结果>>>>>>AtomicLong方式增加计数"+ (threadCount * times) +"万次,共计耗时:" + end2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<threadCount;i++){
            list.add(new Thread(() -> {
                for (int j = 0; j<times; j++){
                    atomicLong.incrementAndGet();
                }
                countDownLatch.countDown();
            }, "my-thread"+i));
        }

        for (Thread thread : list){
            thread.start();
        }
        countDownLatch.await();
    }

    static void testLongAdder(final int threadCount, final int times) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();
        for (int i=0;i<threadCount;i++){
            list.add(new Thread(() -> {
                for (int j = 0; j<times; j++){
                    longAdder.add(1);
                }
                countDownLatch.countDown();
            }, "my-thread"+i));
        }

        for (Thread thread : list){
            thread.start();
        }
        countDownLatch.await();
    }
}
