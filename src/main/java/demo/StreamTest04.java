package demo;

import org.junit.Test;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by huishen on 17/7/21.
 *
 */
public class StreamTest04 {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    // 顺序流，需要拆箱
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .reduce(Long::sum)
            .get();
    }

    // 顺序流，不需要拆箱
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
            .reduce(0L, Long::sum);
    }

    // 并行流，需要拆箱
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .parallel()
            .reduce(Long::sum)
            .get();
    }

    // 并行流，不需要拆箱
    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
            .parallel()
            .reduce(0L, Long::sum);
    }

    public static long measureSumPerf(Function<Long, Long> addr, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            Long sum = addr.apply(n);
            long duration = ((System.nanoTime() - start) / 1_000_000);
            System.out.println("result: " + sum);
            if (duration < fastest)
                fastest = duration;
        }

        return fastest;
    }

    // 3
    @Test
    public void test1() {
        System.out.println("iterativeSum spends: " + measureSumPerf(StreamTest04::iterativeSum, 10_000_000));
    }

    // 142
    @Test
    public void test2() {
        System.out.println("sequentialSum spends: " + measureSumPerf(StreamTest04::sequentialSum, 10_000_000));
    }

    // 与上面方法比较，拆箱的开销
    // 8
    @Test
    public void test2_2() {
        System.out.println("rangedSum spends: " + measureSumPerf(StreamTest04::rangedSum, 10_000_000));
    }

    // 393
    @Test
    public void test3() {
        System.out.println("parallelSum spends: " + measureSumPerf(StreamTest04::parallelSum, 10_000_000));
    }

    // 2
    @Test
    public void test3_2() {
        System.out.println("parallelSum spends: " + measureSumPerf(StreamTest04::parallelRangedSum, 10_000_000));
    }


}
