package demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * @author huishen
 * @date 2019-01-18 14:26
 */
public class AtomicLongDemo {
    private static final int NUM_INC = 8_000_000;

    private static AtomicLong atomicLong = new AtomicLong(0);

    private static void update() {
        atomicLong.set(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range(0, NUM_INC).forEach(i -> {
            Runnable task = () -> atomicLong.updateAndGet(n -> n + 2);
            executorService.submit(task);
        });
        stop(executorService);
        System.out.println(atomicLong.get());
    }

    private static void stop(ExecutorService executorService) {
        try {
            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executorService.isTerminated()) {
                System.out.println("kill tasks");
            }
            executorService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        long s = System.currentTimeMillis();
        update();
        System.out.println("spends: " + (System.currentTimeMillis() - s));
    }
}
