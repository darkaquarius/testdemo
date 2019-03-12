package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by huishen on 18/1/6.
 *
 */

public class RandomDemo {

    /**
     * 生成随机6位数
     */
    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println((int)((Math.random() * 9 + 1) * 100000));
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
        }
    }

    @Test
    public void test3() {
        Random random = new Random();
        int i1 = random.nextInt(10);
        int i2 = random.nextInt(10);
        System.out.println("i1: " + i1);
        System.out.println("i2: " + i2);
    }

    @Test
    public void test4() throws InterruptedException {
        long s  = System.currentTimeMillis();
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService executor = Executors.newFixedThreadPool(size);
        List<Callable<Integer>> callables = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            callables.add(() -> {
                latch.countDown();
                for (int j = 0; j < 10_000; j++) {
                    random.nextInt(500);
                }
                return null;
            });
        }
        executor.invokeAll(callables);
        latch.await();
        System.out.println(System.currentTimeMillis() - s);
    }


    @Test
    public void test5() throws InterruptedException {
        long s  = System.currentTimeMillis();
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService executor = Executors.newFixedThreadPool(size);
        List<Callable<Integer>> callables = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            callables.add(() -> {
                latch.countDown();
                for (int j = 0; j < 10_000; j++) {
                    random.nextInt(500);
                }
                return null;
            });
        }
        executor.invokeAll(callables);
        latch.await();
        System.out.println(System.currentTimeMillis() - s);
    }

    /**
     * size = 10_000，每个线程循环10_000次，41912ms
     */
    @Test
    public void test6() throws InterruptedException {
        long s  = System.currentTimeMillis();
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                latch.countDown();
                for (int j = 0; j < 10_000; j++) {
                    random.nextInt(500);
                }
            }).start();
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - s);
    }

    /**
     * size = 100_000，每个线程循环10_000次，11587ms
     */
    @Test
    public void test7() throws InterruptedException {
        long s  = System.currentTimeMillis();
        int size = 1000;
        CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                ThreadLocalRandom random =ThreadLocalRandom.current();
                for (int j = 0; j < 10_000; j++) {
                    random.nextInt(500);
                }
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println(System.currentTimeMillis() - s);
    }

    public static void main(String[] args) {
        //(10)获取一个随机数生成器
        ThreadLocalRandom random =  ThreadLocalRandom.current();

        //(11)输出10个在0-5（包含0，不包含5）之间的随机数
        for (int i = 0; i < 10; ++i) {
            System.out.println(Thread.currentThread().getName());
            System.out.println(random.nextInt(5));
        }
    }

}
