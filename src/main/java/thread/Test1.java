package thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huishen on 17/3/2.
 * inc++不是原子操作
 */
public class Test1 {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    @Test
    public  void test1() {
        final Test1 test = new Test1();
        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (this) {  // 保证是原子操作
                        test.increase();
                    }
                }
            }).start();
        }

        while (Thread.activeCount() > 1)  //保证前面的线程都执行完
            Thread.yield();
        // inc的值总是小于500000,最根本的原因是"inc++"不是原子操作
        System.out.println(test.inc);
    }
    
    // TODO: 17/7/14  为什么这里加上"synchronized"不能保证是500000？？？？？？？
    // TODO: 17/7/14 看一下ExecutorService的原理？
    @Test
    public void test2() {
        Test1 test = new Test1();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 500; i++) {
            executorService.submit(() -> {
               for (int j = 0; j < 1000; j++) {
                   synchronized (Test1.class) {
                       test.increase();
                   }
               }
            });
        }

        System.out.println(test.inc);
    }

}
