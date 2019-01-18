package thread;

/**
 * Created by huishen on 17/2/27.
 * ExecutorService and Future
 * <p>
 * ExecutorService是线程池的抽象。
 * ScheduledExecutorService可以理解为线程池＋调度的抽象。它在ExecutorService基础上多了schedule的功能。
 * <p>
 * ScheduledExecutorService与ExecutorService接口的实例底层实现都是ThreadPoolExecutor。
 * <p>
 * ExecutorService是Executor子类
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    /**
     * Future
     */
    @Test
    @SuppressWarnings("Duplicates")
    public void test() {

        // 创建单个线程
        // ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 创建一个固定大小的线程执行器，有最大线程数，
        // 超过这个最大线程数，执行器不再创建额外的线程，剩下的任务将被阻塞直到执行器有空闲的线程
        // ExecutorService executorService = Executors.newFixedThreadPool(10);

        // ExecutorService还有一个子类，ThreadPoolExecutor
        // ExecutorService executorService = Executors.newCachedThreadPool();
        // Executor executorService1 = Executors.newCachedThreadPool();

        // ScheduledExecutorService
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);


        // ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
        //     .setNameFormat("demo-pool-%d").build();


        // custome Thread Pool 自定义线程池
        ExecutorService executorService =
            new ThreadPoolExecutor(
                5,
                200,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                runnable -> new Thread(runnable, "MyThread"),
                new ThreadPoolExecutor.AbortPolicy()
            );


        // newWorkStealingPool
        ExecutorService executorService1 = Executors.newWorkStealingPool();

        List<Future<String>> resultList = new ArrayList<>();

        // 创建10个任务并执行
        final int maxThreads = 10;
        for (int i = 0; i < maxThreads; i++) {
            Future<String> future = executorService.submit(new TaskWithResult(i));
            resultList.add(future);
        }
        executorService.shutdown();

        // 遍历任务的结果
        for (Future<String> fs : resultList) {
            try {
                //如果Executor后台线程池还没有完成Callable的计算，这调用返回Future对象的get()方法，会阻塞直到计算完成!!!
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdownNow();
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * FutureTask
     */
    @Test
    public void test2() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<FutureTask<String>> taskList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // FutureTask 是Runnable和Future的子类，FutureTask也有一个构造方法接收Runnable或者Callable类型的参数
            // FutureTask是对Runnable或者Callable的包装
            FutureTask<String> task = new FutureTask<>(new TaskWithResult(i));
            taskList.add(task);
            executorService.submit(task);
        }

        for (FutureTask<String> task : taskList) {
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void test3() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<String>> resultList = new ArrayList<>();

        // 创建10个任务并执行
        final int maxThreads = 10;
        for (int i = 0; i < maxThreads; i++) {
            Future<String> future = executorService.submit(new TaskWithResult(i));
            resultList.add(future);
        }
        executorService.shutdown();

        // todo
        // for () {
            //  CompletableFutureDemo
        // }

    }

    static class TaskWithResult implements Callable<String> {

        private int id;

        public TaskWithResult(int id) {
            this.id = id;
        }

        /**
         * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
         *
         * @return String
         * @throws Exception
         */
        @Override
        public String call() throws Exception {
            System.out.println("call()方法被自动调用,干活！！！" + Thread.currentThread().getName());
            // 模拟一个错误
            // if (new Random().nextBoolean())
            //     throw new TaskException("Meet error in task." + Thread.currentThread().getName());
            // 一个模拟耗时的操作
            Thread.sleep(1000);
            return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
        }

    }

    static class TaskException extends Exception {
        public TaskException(String message) {
            super(message);
        }
    }

}

