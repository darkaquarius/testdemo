package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by huishen on 17/10/23.
 *
 */

public class Sync5 implements Runnable{

    @Override
    public void run() {
        test();
    }

    @SuppressWarnings("Duplicates")
    public void test() {
        synchronized (Sync5.class) {
            System.out.println("start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
        }
    }

    // public static synchronized void test() {
    //         System.out.println("start");
    //         try {
    //             Thread.sleep(1000);
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //         System.out.println("end");
    // }

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<?>> resultList = new ArrayList<>();

        // Sync5 sync5 = new Sync5();
        for (int i = 0; i < 3; i++) {
            Sync5 sync5 = new Sync5();
            Future<?> submit = executorService.submit(sync5);
            resultList.add(submit);
        }
        executorService.shutdown();

        for (Future<?> f : resultList) {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdownNow();
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void test2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<?>> resultList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Future<?> submit = executorService.submit(() -> {
                System.out.println(this);
                synchronized (this) {
                    System.out.println("start");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end");
                }
            });
            resultList.add(submit);
        }
        executorService.shutdown();
        for (Future<?> f : resultList) {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdown();
                e.printStackTrace();
            }
        }

    }

}
