package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by huishen on 17/7/19.
 *
 */

public class StreamTest03 {

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

    // 找出2011年发生的所有交易，并按照交易额排序（从高到低）
    @Test
    public void test01() {
        List<Transaction> ret1 = transactions
            .stream()
            .filter(t -> 2011 == t.getYear())
            .sorted(Comparator.comparing(Transaction::getValue)
                .reversed()
                .thenComparing(Transaction::getYear)
            )
            .collect(Collectors.toList());
        System.out.println(ret1);
    }

    // 交易员都在哪些不同的城市工作过?
    @Test
    public void test02() {
        List<String> ret2 = transactions
            .stream()
            .map(t -> t.getTrader().getCity())
            .distinct()
            .collect(Collectors.toList());
        System.out.println(ret2);
    }

    // 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test03() {
        List<Trader> traders = transactions
            .stream()
            .map(Transaction::getTrader)
            .filter(trader -> "Cambridge".equals(trader.getCity()))
            // .sorted(Comparator.<Trader, Comparable<String>>comparing((Function<Trader, String>) Trader::getName))
            .collect(Collectors.toList());
        for (Trader trader : traders) {
            System.out.println(trader);
        }
    }

    // 返回所有交易员的姓名按字字符串，字母顺序排序
    @Test
    public void test04() {
        List<String> collect = transactions
            .stream()
            .flatMap(transaction -> Arrays.stream(transaction.getTrader().getName().split("")))
            .sorted()
            .collect(Collectors.toList());
    }

    // 有没有交易员在米兰工作的
    @Test
    public void test05() {
        boolean milan = transactions
            .stream()
            .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
    }

    // 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test06() {
        Map<String, Transaction> collect = transactions
            .stream()
            .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
            .collect(Collectors.toMap(transaction -> transaction.getTrader().getName(), Function.identity()));
    }

    // 所有交易中，最高的交易额是多少
    @Test
    public void test07() {
        Optional<Integer> reduce = transactions
            .stream()
            .map(Transaction::getValue)
            .reduce(Integer::sum);
    }

    // 找到交易额最小的交易
    @Test
    public void test08() {
        Optional<Transaction> reduce = transactions
            .stream()
            .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

    // 找到交易额最小的交易
    @Test
    public void test08_02() {
        Optional<Transaction> min = transactions
            .stream()
            .min(Comparator.comparing(Transaction::getValue));
    }

}
