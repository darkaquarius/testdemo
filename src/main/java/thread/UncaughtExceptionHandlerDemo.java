package thread;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huishen
 * @date 2019-08-06 10:31
 */
public class UncaughtExceptionHandlerDemo {

    /**
     * new Thread()手动启动一个线程
     */
    @Test
    public void test1() throws InterruptedException {
        // 方法1
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        Thread thread = new Thread(new Task());
        // 方法2
        // thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }

    /**
     * 线程池#execute()方法
     *
     * 线程池中设置setUncaughtExceptionHandler  的方式1：ThreadPoolTask
     */
    @SuppressWarnings("AlibabaThreadPoolCreation")
    @Test
    public void test2() {
        ExecutorService exec = Executors.newCachedThreadPool();
        // ThreadPoolTask,加了setUncaughtExceptionHandler()方法
        exec.execute(new ThreadPoolTask());
        exec.shutdown();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
    }

    /**
     * 线程池#execute()方法
     *
     * 线程池中设置setUncaughtExceptionHandler  的方式2
     */
    @Test
    public void test3() {
        AtomicInteger i = new AtomicInteger(1);
        ExecutorService exec =
            new ThreadPoolExecutor(
                5,
                200,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                // new ArrayBlockingQueue<Runnable>(10,true),
                runnable -> {
                    Thread thread = new Thread(runnable, "MyThread" + i.getAndIncrement());
                    // 在ThreadFactory中加了setUncaughtExceptionHandler()方法
                    thread.setUncaughtExceptionHandler(new ExceptionHandler());
                    thread.setDaemon(true);
                    return thread;
                },
                new ThreadPoolExecutor.AbortPolicy()
            );
        // Task, 没有setUncaughtExceptionHandler()方法
        exec.execute(new Task());
        exec.shutdown();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

    }

    /**
     * 线程池#submit()方法
     */
    @SuppressWarnings("AlibabaThreadPoolCreation")
    @Test
    public void test4() {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<?> future = exec.submit(new Task());
        exec.shutdown();

        try {
            future.get();
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println("==Exception: "+e.getMessage());
        }
    }


    private class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(3 / 2);
            System.out.println(3 / 0);
            System.out.println(3 / 1);
        }
    }

    private class ThreadPoolTask implements Runnable {
        @Override
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
            System.out.println(3 / 2);
            System.out.println(3 / 0);
            System.out.println(3 / 1);
        }
    }

    private class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("==Exception: " + e.getMessage());
        }
    }

}
