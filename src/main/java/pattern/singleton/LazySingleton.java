package pattern.singleton;

import java.util.concurrent.CountDownLatch;

/**
 * 懒汉式单例
 */

public class LazySingleton {

    private static LazySingleton instance = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (null == instance) {
            instance = new LazySingleton();
        }
        return instance;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) throws InterruptedException {
//        // 单线程下，懒汉式单例没有问题
//        LazySingleton pattern.singleton = LazySingleton.getInstance();
//        LazySingleton singleton1 = LazySingleton.getInstance();
//        LazySingleton singleton2 = LazySingleton.getInstance();

        // 多线程下，懒汉式会出现问题
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(() -> {
            LazySingleton singleton = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "  " + instance);
            countDownLatch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            LazySingleton singleton = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "  " + instance);
            countDownLatch.countDown();
        });

        thread1.start();
        thread2.start();

        countDownLatch.await();
        System.out.println("main end");
    }


}
