package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * Created by huishen on 17/11/28.
 *
 */
public class StreamTest05 {
    private static List<Transaction> transactions;

    static {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

    }

    @Test
    public void test1() {
        List<Transaction> collect = transactions.stream()
            .filter(t -> t.getYear() == 2011)
            .sorted(Comparator.comparing(Transaction::getValue))
            .collect(Collectors.toList());
    }

    @Test
    public void test2() {
        List<String> collect = transactions.stream()
            .map(t -> t.getTrader().getCity())
            .distinct()
            .collect(Collectors.toList());
    }

    @Test
    public void test3() {
        List<Transaction> collect = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .sorted(Comparator.comparing(t -> t.getTrader().getName()))
            .collect(Collectors.toList());
    }

    @Test
    public void test4() {
        List<String> collect = transactions.stream()
            .flatMap(t -> Arrays.stream(t.getTrader().getName().split("")))
            .sorted()
            .collect(Collectors.toList());
    }

    @Test
    public void test5() {
        boolean b = transactions.stream()
            .anyMatch(t -> "Milan".equals(t.getTrader().getCity()));
    }

    @Test
    public void test6_1() {
        Integer reduce = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .mapToInt(Transaction::getValue)
            .reduce(0, (a, b) -> a + b);
    }

    @Test
    public void test6_2() {
        Integer reduce = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .mapToInt(Transaction::getValue)
            .reduce(0, Integer::sum);
    }


    @Test
    public void test6_3() {
        int sum = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .mapToInt(Transaction::getValue)
            .sum();
    }

    @Test
    public void test6_4() {
        Integer collect = transactions.stream()
            .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
            .collect(Collectors.summingInt(Transaction::getValue));
    }

    @Test
    public void test7() {
        OptionalInt max = transactions.stream()
            .mapToInt(Transaction::getValue)
            .max();

        int i = max.orElse(0);
    }

    /**
     * 找List中最小的一个元素
     * 1. 排序，选第一个
     * 2. min()
     * 3. reduce() lambda表达式
     */
    @Test
    public void test8_1() {
        Optional<Transaction> firstOptional = transactions.stream()
            .sorted(Comparator.comparing(Transaction::getValue))
            .findFirst();

        Transaction transaction = firstOptional.orElse(null);
    }

    @Test
    public void test8_2() {
        Optional<Transaction> min = transactions.stream()
            .min(Comparator.comparing(Transaction::getValue));

        Transaction transaction = min.orElse(null);
    }

    @Test
    public void test8_3() {
        Optional<Transaction> reduce = transactions.stream()
            .reduce((a, b) -> a.getValue() < b.getValue() ? a : b);

        Transaction transaction = reduce.orElse(null);
    }

}
