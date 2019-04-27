package demo;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * 并行流性能测试
 *
 * @author huishen
 * @date 2019-04-16 12:07
 */
public class StreamTest04_1 {

    private void blockingFunc(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 阻塞操作的串行流
     * 12s
     */
    @Test
    public void testSequential() {
        LongStream.rangeClosed(1, 4)
            .peek(x -> {
                blockingFunc(2_000);
            })
            .sum();
    }

    /**
     * 阻塞操作的并行流
     * 3s
     */
    @Test
    public void testParallel() {
        long sum = LongStream.rangeClosed(1, 8)
            .parallel()
            .peek(x -> {
                blockingFunc(1_000);
            })
            .sum();
        System.out.println(sum);
    }

    /**
     * https://www.jianshu.com/p/a616afea2f09
     */
    @Test
    public void test() {
        int sum = IntStream.range(1, 20)
            .parallel()
            .peek(n -> System.out.print(n + "\t"))
            .skip(5)
            .limit(5)
            .sum();
        System.out.println();
        System.out.println("sum is :" + sum);
    }

    /**
     * 单独跑task1，很快
     * 同时跑task1和task2，因为会"共享线程池"，会相互阻塞
     * parallel stream会共享ForkJoinPool.commonPool线程池
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test1() throws InterruptedException {
        long s = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable task1 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            LongStream.rangeClosed(1, 8)
                // 共享ForkJoinPool.commonPool线程池
                .parallel()
                .peek(x -> {
                    System.out.println("task1, " + x + ", " + Thread.currentThread().getName());
                    blockingFunc(1_000);
                })
                .sum();
            System.out.println("task1, costs: " + (System.currentTimeMillis() - s));
            countDownLatch.countDown();
        };
        Runnable task2 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            LongStream.rangeClosed(1, 8)
                // 共享ForkJoinPool.commonPool线程池
                .parallel()
                .peek(x -> {
                    System.out.println("task2, " + x + ", " + Thread.currentThread().getName());
                    blockingFunc(5_000);
                })
                .sum();
            System.out.println("task2, costs: " + (System.currentTimeMillis() - s));
            countDownLatch.countDown();
        };
        new Thread(task1).start();
        new Thread(task2).start();
        countDownLatch.await();
    }

    /**
     * 每次新建一个ForkJoinPool
     * parallel stream会跑在各自的ForkJoinPool线程池中
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test2() throws InterruptedException {
        long s = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable task1 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            // 新建ForkJoinPool
            ForkJoinPool forkJoinPool = new ForkJoinPool(4);
            forkJoinPool.submit(() -> {
                LongStream.rangeClosed(1, 8)
                    .parallel()
                    .peek(x -> {
                        System.out.println("task1, " + x + ", " + Thread.currentThread().getName());
                        blockingFunc(1_000);
                    })
                    .sum();
                System.out.println("task1, costs: " + (System.currentTimeMillis() - s));
                countDownLatch.countDown();
            });
        };
        Runnable task2 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            // 新建ForkJoinPool
            ForkJoinPool forkJoinPool = new ForkJoinPool(4);
            forkJoinPool.submit(() -> {
                LongStream.rangeClosed(1, 8)
                    .parallel()
                    .peek(x -> {
                        System.out.println("task2, " + x + ", " + Thread.currentThread().getName());
                        blockingFunc(5_000);
                    })
                    .sum();
                System.out.println("task2, costs: " + (System.currentTimeMillis() - s));
                countDownLatch.countDown();
            });
        };
        new Thread(task1).start();
        new Thread(task2).start();
        countDownLatch.await();
    }

}
