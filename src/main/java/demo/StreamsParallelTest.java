package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Parallel Streams , 并行流提高性能
 * <p>
 * 流可以是顺序的也可以是并行的。顺序流的操作是在单线程上执行的，而并行流的操作是在多线程上并发执行的。
 */
public class StreamsParallelTest {

    // 顺序流
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
            .limit(n)
            .reduce(0L, Long::sum);
        // .mapToLong(Long::longValue)
        // .sum();
    }

    // 并行流1
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
        // System.out.println("Sequential sum done in:" + measureSumPerf(StreamsParallelTest::sequentialSum, 10_000_000));
        // 222
        // System.out.println("Parallel sum done in:" + measureSumPerf(StreamsParallelTest::parallelSum, 10_000_000));
        // 3
        // System.out.println("Iterative sum done in:" + measureSumPerf(StreamsParallelTest::iterativeSum, 10_000_000));
        // 7
        // System.out.println("ranged sum done in:" + measureSumPerf(StreamsParallelTest::rangedSum, 10_000_000));
        // 3
        System.out.println("parallel ranged sum done in:" + measureSumPerf(StreamsParallelTest::parallelRangedSum, 10_000_000));

        // System.out.println("SideEffect parallel sum done in:" + measureSumPerf(StreamsParallelTest::sideEffectSum, 10_000_000));
    }

    public static class Accumulator {
        private long total = 0;

        public void add(long value) {
            total += value;
        }
    }

    @Test
    public void test() {
        String[] array = {"a", "b", "a", "c"};
        Map<String, Integer> wordsCount =
            Arrays
                .stream(array)
                .parallel()
                .collect(Collectors.toMap(s -> s, s -> 1, (i, j) -> i + j));
    }

    /**
     * https://www.cnblogs.com/puyangsky/p/7608741.html
     *
     * stream.parallel.forEach()中执行的操作并非线程安全
     *
     * 串行执行的大小：10000
     * 并行执行的大小：9699
     * 加锁并行执行的大小：10000
     */
    @Test
    public void test2 () {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        Lock lock = new ReentrantLock();
        IntStream.range(0, 10000).forEach(list1::add);

        IntStream.range(0, 10000).parallel().forEach(list2::add);

        IntStream.range(0, 10000).forEach(i -> {
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("加锁并行执行的大小：" + list3.size());
    }

    /**
     * https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#concurrent_reduction
     *
     * toConcurrentMap()
     *
     * groupingByConcurrent()
     *
     */
    @Test
    public void testConcurrency() {
        // 在并行流中，toConcurrentMap()的性能高于toMap()
        ConcurrentMap<Integer, Integer> collect = IntStream.range(0, 100000)
            .parallel()
            .boxed()
            .collect(Collectors.toConcurrentMap(x -> x, y -> y));

        // groupingByConcurrent会返回ConcurrentMap
        ConcurrentMap<Integer, List<Integer>> collect1 = IntStream.range(0, 100000)
            .parallel()
            .boxed()
            // .collect(Collectors.toList())
            .collect(Collectors.groupingByConcurrent(x -> x));
    }

    /**
     *
     * https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#ordering
     *
     * 流计算使用内部迭代，因此，在并行流中，计算机会以效率最高的顺序来执行元素。
     * forEachOrdered()可以在并行流中保持顺序，但是就失去了并行计算的收益。
     *
     *
     * listOfIntegers:
     * 1 2 3 4 5 6 7 8
     * listOfIntegers sorted in reverse order:
     * 8 7 6 5 4 3 2 1
     * Parallel stream
     * 6 3 5 4 7 8 2 1
     * Another parallel stream:
     * 6 5 1 7 8 4 3 2
     * With forEachOrdered:
     * 8 7 6 5 4 3 2 1
     */
    @Test
    public void testOrder() {
        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8 };
        List<Integer> listOfIntegers =
            new ArrayList<>(Arrays.asList(intArray));

        System.out.println("listOfIntegers:");
        listOfIntegers
            .stream()
            .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("listOfIntegers sorted in reverse order:");
        Comparator<Integer> normal = Integer::compare;
        Comparator<Integer> reversed = normal.reversed();
        Collections.sort(listOfIntegers, reversed);
        listOfIntegers
            .stream()
            .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Parallel stream");
        listOfIntegers
            .parallelStream()
            .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Another parallel stream:");
        listOfIntegers
            .parallelStream()
            .forEach(e -> System.out.print(e + " "));
        System.out.println("");

        // 在
        System.out.println("With forEachOrdered:");
        listOfIntegers
            .parallelStream()
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");
    }

    /**
     * https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#interference
     *
     * Exception caught: java.util.ConcurrentModificationException
     *
     * stream中不能修改原始的值，这里指的是listOfStrings
     */
    @Test
    public void testInterference() {
        try {
            List<String> listOfStrings =
                new ArrayList<>(Arrays.asList("one", "two"));

            // This will fail as the peek operation will attempt to add the
            // string "three" to the source after the terminal operation has
            // commenced.

            String concatenatedString = listOfStrings
                .stream()
                // Don't do this! Interference occurs here.
                .peek(s -> listOfStrings.add("three"))    // fatal!
                // .peek(s -> { s = s + "three"; })    // it's ok
                .reduce((a, b) -> a + " " + b)
                .get();
            System.out.println("Concatenated string: " + concatenatedString);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.toString());
        }
    }

    /**
     * https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html#stateful_lambda_expressions
     *
     * Serial stream:
     * 1 2 3 4 5 6 7 8
     * 1 2 3 4 5 6 7 8
     * Parallel stream:
     * 1 2 3 4 5 6 7 8
     * 2 8 3 1 4 7 5 6
     *
     * parallelStorage是一个外部变量，每次运行的时候，parallelStorage都会变化，也即：lamda表达式是有状态的
     */
    @Test
    public void testStatefulLambdaExpressions() {
        List<Integer> serialStorage = new ArrayList<>();

        Integer[] intArray = {1, 2, 3, 4, 5, 6, 7, 8 };
        List<Integer> listOfIntegers =
            new ArrayList<>(Arrays.asList(intArray));

        System.out.println("Serial stream:");
        listOfIntegers
            .stream()
            // Don't do this! It uses a stateful lambda expression.
            .map(e -> { serialStorage.add(e); return e; })
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        serialStorage
            .stream()
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        System.out.println("Parallel stream:");
        List<Integer> parallelStorage = Collections.synchronizedList(
            new ArrayList<>());
        // List<Integer> parallelStorage = new ArrayList<>();  // error!
        listOfIntegers
            .parallelStream()
            // Don't do this! It uses a stateful lambda expression.
            .map(e -> { parallelStorage.add(e); return e; })
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");

        parallelStorage
            .stream()
            .forEachOrdered(e -> System.out.print(e + " "));
        System.out.println("");
    }


}
