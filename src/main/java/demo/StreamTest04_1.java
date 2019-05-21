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
     *
     * 4s
     */
    @Test
    public void testSequential() {
        LongStream.rangeClosed(1, 4)
            .peek(x -> {
                blockingFunc(1_000);
            })
            .sum();
    }

    /**
     * 阻塞操作的并行流
     *
     * 1s
     */
    @Test
    public void testParallel() {
        long sum = LongStream.rangeClosed(1, 4)
            .parallel()
            .peek(x -> {
                blockingFunc(1_000);
            })
            .sum();
        System.out.println(sum);
    }

    /**
     * https://www.jianshu.com/p/a616afea2f09
     *
     * todo
     * 1.在并行流下，有状态操作(StatefulOp)可能会直接对原始流(Head)的遍历产生影响，如skip和limit对原始流进行切片(SliceOp)。
     * 2.在并行流下，会用ForkJoinTask进行并行计算，而 ForkJoinTask 会对任务(流)进行分割，同时会丢弃掉estimateSize=0的子任务(流)。
     *   切片范围不同，会对ForkJoinTask分割子任务产生不同的影响，从而影响程序运行的结果。
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

        // task1
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

        // task2
        Runnable task2 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            LongStream.rangeClosed(1, 8)
                // 共享ForkJoinPool.commonPool线程池
                .parallel()
                .peek(x -> {
                    System.out.println("task2, " + x + ", " + Thread.currentThread().getName());
                    blockingFunc(1_000);
                })
                .sum();
            System.out.println("task2, costs: " + (System.currentTimeMillis() - s));
            countDownLatch.countDown();
        };

        // 启动task1和task2
        new Thread(task1).start();
        new Thread(task2).start();

        // 主线程等待
        countDownLatch.await();
    }

    /**
     * 每次新建一个ForkJoinPool
     * parallel stream会跑在各自的ForkJoinPool线程池中
     *
     * 启动visualvm，可以看到是有2个ForkJoinPool.commonPool-worker-x的。
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test2() throws InterruptedException {
        // 等待visualvm启动
        Thread.sleep(5_000);

        long s = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        // task1
        Runnable task1 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            // ************* 新建ForkJoinPool ************
            ForkJoinPool forkJoinPool = new ForkJoinPool(4);
            forkJoinPool.submit(() -> {
                LongStream.rangeClosed(1, 8)
                    .parallel()
                    .peek(x -> {
                        System.out.println("task1, " + x + ", " + Thread.currentThread().getName());
                        blockingFunc(20_000);
                    })
                    .sum();
                System.out.println("task1, costs: " + (System.currentTimeMillis() - s));
                countDownLatch.countDown();
            });
        };

        // task2
        Runnable task2 = () -> {
            System.out.println("current thread: " + Thread.currentThread().getName());
            // ************* 新建ForkJoinPool ************
            ForkJoinPool forkJoinPool = new ForkJoinPool(4);
            forkJoinPool.submit(() -> {
                LongStream.rangeClosed(1, 8)
                    .parallel()
                    .peek(x -> {
                        System.out.println("task2, " + x + ", " + Thread.currentThread().getName());
                        blockingFunc(20_000);
                    })
                    .sum();
                System.out.println("task2, costs: " + (System.currentTimeMillis() - s));
                countDownLatch.countDown();
            });
        };

        // 启动task1和task2
        new Thread(task1).start();
        new Thread(task2).start();

        // 主线程等待
        countDownLatch.await();
    }

}
