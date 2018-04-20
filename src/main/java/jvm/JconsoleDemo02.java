package jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author huishen
 * @date 18/3/22 下午2:16
 *
 * Jconsole监控代码：线程监控
 * 线程等待演示代码
 *
 * 线程长时间停顿的主要原因有：等待外部资源、死循环、锁等待(活锁和死锁)
 *
 */
public class JconsoleDemo02 {

    /**
     * 线程死循环演示
     */
    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) ;
            }
        }, "testBusyThread");

        thread.start();
    }

    /**
     * 线程锁等待演示
     */
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");

        thread.start();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object obj = new Object();
        createLockThread(obj);
    }


}
