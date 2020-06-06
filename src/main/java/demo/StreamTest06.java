package demo;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by huishen on 17/11/29.
 * 将数字按质数和非质数分区
 */
public class StreamTest06 {

    @Test
    public void test() {
        long s = System.currentTimeMillis();
        System.out.println(isPrime(49999));
        System.out.println("test spends: " + (System.currentTimeMillis() - s));
    }

    private boolean isPrime(int candicate) {
        boolean b = IntStream.rangeClosed(2, candicate)
            .noneMatch(i -> candicate % i == 0);

        return b;
    }

    /**
     * 默认情况下，并行流的线程数是CPU核心数-1
     */
    @Test
    public void testParallelThreads() {
        System.out.println("cpu: " + Runtime.getRuntime().availableProcessors());
        IntStream.range(1, 100).parallel().forEach(printThreadNameIntConsumer);
    }

    /**
     * 使用forkJoinPool来控制并行流的线程数，让并行流在forkJoinPool中执行
     */
    @Test
    public void testParallelThreads2() throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        forkJoinPool.execute(() -> {
            IntStream.range(1, 100).parallel().forEach(printThreadNameIntConsumer);
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.MINUTES);
    }

    private IntConsumer printThreadNameIntConsumer =  i -> System.out.println("Thread:" + Thread.currentThread().getName());

}
