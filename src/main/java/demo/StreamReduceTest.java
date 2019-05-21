package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author huishen
 * @date 2019-05-20 15:57
 */
public class StreamReduceTest {
    private static List<Transaction> transactions;

    static {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

    }

    /**
     * stream().reduce(...)
     * <p>
     * U identity                                    初始值
     * BiFunction<U, ? super T, U> accumulator       累加器
     * BinaryOperator<U> combiner                    累计函数
     */
    @Test
    public void test1() {
        Integer reduce = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .reduce(0, (s, t) -> s + t.getValue(), Integer::sum);
    }

    /**
     * Collectors.reducing(...):
     * U identity:                                 初始值
     * Function<? super T, ? extends U> mapper:    转换函数
     * BinaryOperator<U> op:                       累计函数
     */
    @Test
    public void test2() {
        Integer collect = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .collect(Collectors.reducing(0, Transaction::getValue, Integer::sum));
    }

    /**
     * 将10个整型数值的list，先乘以2，再转成了10个double类型的set
     * <p>
     * 使用 自定义Collector 实现。
     * <p>
     * Collector:
     * Supplier<A> supplier()                    生成一个初始容器，用于存放转换的数据。
     * BiConsumer<A, T> accumulator()            将初始容器与Stream中的每个元素进行计算
     * BinaryOperator<A> combiner()              方法用于在并发Stream中，将多个容器组合成一个容器
     * Function<A, R> finisher()                 将初始容器转换成最终的值
     * Set<Characteristics> characteristics()    返回该Collector具有的哪些特征
     */
    @Test
    public void test3() {
        List<Integer> nums = new ArrayList<>();
        Set<Double> result = new HashSet<>();

        // 填充原始数据，nums中填充0-9 10个数
        IntStream.range(0, 10).forEach(nums::add);

        // 实现Collector接口
        result = nums.stream().parallel().collect(new Collector<Integer, Container, Set<Double>>() {

            @Override
            public Supplier<Container> supplier() {
                return Container::new;
            }

            @Override
            public BiConsumer<Container, Integer> accumulator() {
                return Container::accumulate;
            }

            @Override
            public BinaryOperator<Container> combiner() {
                return Container::combine;
            }

            @Override
            public Function<Container, Set<Double>> finisher() {
                return Container::getResult;
            }

            @Override
            public Set<Characteristics> characteristics() {
                // 固定写法
                return Collections.emptySet();
            }
        });
    }

    private Double compute(int num) {
        return (double) (2 * num);
    }

    class Container {
        // 定义本地的result
        public Set<Double> set;

        public Container() {
            this.set = new HashSet<>();
        }

        public Container accumulate(int num) {
            this.set.add(compute(num));
            return this;
        }

        public Container combine(Container container) {
            this.set.addAll(container.set);
            return this;
        }

        public Set<Double> getResult() {
            return this.set;
        }
    }

    /**
     * 重新写test3()方法
     *
     * 并行流
     */
    @Test
    public void test3Copy() {
        //
        List<Integer> nums = new ArrayList<>();
        IntStream.range(0, 10).forEach(nums::add);

        List<Double> result = new ArrayList<>();
        CopyOnWriteArrayList<Double> result2 = new CopyOnWriteArrayList<>();


        // 方法一, map()
        // result = nums
        //     .parallelStream()
        //     .map(x -> (double) x * 2)
        //     .collect(Collectors.toList());

        // 方法二，需要同步处理
        // nums
        //     .parallelStream()
        //     .forEach(x -> {
        //         synchronized (this) {
        //             result.add((double) x * 2);
        //         }
        //     });

        // 方法三，CopyOnWriteArrayList,
        // nums.parallelStream()
        //     .forEach(x -> {
        //         result2.add((double) x * 2);
        //     });

        // 方法四，使用reduce
        // some wrong!
        // ArrayList<Double> ret =
        //     nums
        //         .stream()
        //         // .parallel()
        //         .reduce(
        //             new ArrayList<>(),
        //             (list, num) -> {
        //                 list.add((double) num * 2);
        //                 return list;
        //             },
        //             (list1, list2) -> {
        //                 list1.addAll(list2);
        //                 return list1;
        //             });

        // 方法五，使用collect(Collectors.reducing(...))
        // some wrong!
        // ArrayList<Object> list = new ArrayList<>();
        // ArrayList<Object> collect = nums
        //     .parallelStream()
        //     .collect(Collectors.reducing(
        //         list,
        //         (list1, list2) -> {
        //         list1.addAll(list2);
        //         return list1;
        //     }));

        // 方法六，Collect.of()
        // ArrayList<Double> collect = nums
        //     .parallelStream()
        //     .collect(Collector.of(
        //         () -> new ArrayList<Double>(),
        //         (list, num) -> {
        //             list.add((double) num * 2);
        //         },
        //         (list1, list2) -> {
        //             list1.addAll(list2);
        //             return list1;
        //         },
        //         list -> list,
        //         new Collector.Characteristics[]{}
        //     ));
    }

}
