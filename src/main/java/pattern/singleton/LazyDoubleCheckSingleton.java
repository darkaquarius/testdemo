package pattern.singleton;

import java.util.concurrent.CountDownLatch;

/**
 * double check 的懒汉式单例
 */
public class LazyDoubleCheckSingleton {

    private volatile static LazyDoubleCheckSingleton instance = null;

    private LazyDoubleCheckSingleton() {

    }

    public static LazyDoubleCheckSingleton getInstance() {
        if (null == instance) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (null == instance) {
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

    public void testtmp() {
        LazyDoubleCheckSingleton lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
    }

    public static LazyDoubleCheckSingleton getInstanceWithoutInit() {
        return instance;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) throws InterruptedException {

        // 多线程下，懒汉式会出现问题
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread thread1 = new Thread(() -> {
            LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "  " + instance);
            countDownLatch.countDown();
        }, "Thread1");

        Thread thread2 = new Thread(() -> {
            LazyDoubleCheckSingleton singleton = LazyDoubleCheckSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "  " + instance);
            countDownLatch.countDown();
        }, "Thread2");

        thread1.start();
        thread2.start();

        Thread thread3 = new Thread(() -> {
            synchronized (LazyDoubleCheckSingleton.getInstanceWithoutInit()) {
                LazyDoubleCheckSingleton instance = LazyDoubleCheckSingleton.getInstanceWithoutInit();
                System.out.println(Thread.currentThread().getName() + "  " + instance);
            }
            countDownLatch.countDown();
        }, "Thread3");

        thread3.start();

        countDownLatch.await();
        System.out.println("main end");
    }

}
