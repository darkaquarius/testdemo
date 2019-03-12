package thread;

import org.junit.Test;

/**
 * @author huishen
 * @date 2019-01-27 15:52
 */
public class ThreadLocalDemo02 {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * ThreadLocal.set(null)的情况
     * null被当做一个合法值保存在ThreadLocalMap的Entry中的value中
     * ThreadLocal.get()就会把null值取出来。
     */
    @Test
    public void test1() {
        String str1 = "main thread";
        threadLocal.set(str1);
        System.out.println("main thread, threadLocal value: " + threadLocal.get());
        Thread thread1 = new Thread(() -> {
            // threadLocal.set("other thread");
            threadLocal.set(null);
            System.out.println("thread1, threadLocal value: " + threadLocal.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread1, threadLocal value: " + threadLocal.get());
        });
        thread1.start();
        System.out.println("main thread, threadLocal value: " + threadLocal.get());

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }

}
