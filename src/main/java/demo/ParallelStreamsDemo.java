package demo;

import org.junit.Test;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Parallel Streams , 并行流提高性能
 * <p>
 * 流可以是顺序的也可以是并行的。顺序流的操作是在单线程上执行的，而并行流的操作是在多线程上并发执行的。
 */
public class ParallelStreamsDemo {

    // 顺序流
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .reduce(0L, Long::sum);
        // .mapToLong(Long::longValue)
        // .sum();
    }

    // 并行流
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .parallel()
            .reduce(0L, Long::sum);
    }

    // 迭代
    public static long iterativeSum(long n) {
        long sum = 0;
        for (long i = 1L; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
            .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
            .parallel()
            .reduce(0L, Long::sum);
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            Long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }

    @Test
    public void test1() {
        // 149
        // System.out.println("Sequential sum done in:" + measureSumPerf(ParallelStreamsDemo::sequentialSum, 10_000_000));
        // 222
        // System.out.println("Parallel sum done in:" + measureSumPerf(ParallelStreamsDemo::parallelSum, 10_000_000));
        // 3
        // System.out.println("Iterative sum done in:" + measureSumPerf(ParallelStreamsDemo::iterativeSum, 10_000_000));
        // 7
        // System.out.println("ranged sum done in:" + measureSumPerf(ParallelStreamsDemo::rangedSum, 10_000_000));
        // 3
        System.out.println("parallel ranged sum done in:" + measureSumPerf(ParallelStreamsDemo::parallelRangedSum, 10_000_000));

        // System.out.println("SideEffect parallel sum done in:" + measureSumPerf(ParallelStreamsDemo::sideEffectSum, 10_000_000));
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }


}
