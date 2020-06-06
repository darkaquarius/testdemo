package demo;

import org.junit.Test;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConcurrentHashMapDemo {

    //循环次数
    private static int LOOP_COUNT = 10_000_000;

    //线程数量
    private static int THREAD_COUNT = 10;

    //元素数量
    private static int ITEM_COUNT = 10;

    private Map<String, Long> normaluse() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    //获得一个随机的Key
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    synchronized (freqs) {
                        if (freqs.containsKey(key)) {
                            //Key存在则+1
                            freqs.put(key, freqs.get(key) + 1);
                        } else {
                            //Key不存在则初始化为1
                            freqs.put(key, 1L);
                        }
                    }
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        return freqs;
    }

    private Map<String, Long> gooduse1() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
                    freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
    }

    private Map<String, Long> gooduse2() throws InterruptedException {
        ConcurrentHashMap<String, Long> freqs = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    // compute(), computeIfAbsent(), computeIfPresent()都可以看成是原子操作
                    freqs.compute(key, (k, oldVaule) -> {
                        if (oldVaule == null) {
                            return 1L;
                        } else {
                            return oldVaule + 1;
                        }
                    });
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        return freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
    }

//    @Test
//    public void test01() throws InterruptedException {
//        Map<String, Long> map = gooduse2();
//        long sum = map.values().stream().mapToLong(Long::longValue).sum();
//        System.out.println("sum :" + sum);
//    }


    @Test
    public void test1() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normaluse");
        Map<String, Long> normaluse = normaluse();
        stopWatch.stop();
        //校验元素数量
        Assert.isTrue(normaluse.size() == ITEM_COUNT, "normaluse size error");
        //校验累计总数
        Assert.isTrue(normaluse.entrySet().stream()
                        .mapToLong(item -> item.getValue()).reduce(0, Long::sum) == LOOP_COUNT
                , "normaluse count error");
        stopWatch.start("gooduse1");
        Map<String, Long> gooduse1 = gooduse1();
        stopWatch.stop();
        Assert.isTrue(gooduse1.size() == ITEM_COUNT, "gooduse size error");
        Assert.isTrue(gooduse1.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "gooduse1 count error");
        stopWatch.start("gooduse2");
        Map<String, Long> gooduse2 = gooduse1();
        stopWatch.stop();
        Assert.isTrue(gooduse2.size() == ITEM_COUNT, "gooduse size error");
        Assert.isTrue(gooduse2.entrySet().stream()
                        .mapToLong(item -> item.getValue())
                        .reduce(0, Long::sum) == LOOP_COUNT
                , "gooduse2 count error");
        System.out.println(stopWatch.prettyPrint());
    }

}
