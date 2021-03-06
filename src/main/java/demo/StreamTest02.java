package demo;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by huishen on 17/7/19.
 *
 */
public class StreamTest02 {

    // 可以从函数生成无限流
    // iterate
    @Test
    public void testIterable() throws InterruptedException {
        Stream.iterate(0, n -> n + 2)
            .limit(50)
            .forEach(System.out::println);
    }

    // 可以从函数生成无限流
    //
    @Test
    public void testGenerate() {
        Stream.generate(Math::random)
            .limit(5)
            .forEach(System.out::println);
    }


    /**
     * 不干扰，这里正常
     */
    @Test
    public void testNointerference1() {
        List<String> wordList = new ArrayList<>();
        wordList.add("zhangsan");
        wordList.add("lisi");
        wordList.add("wangwu");
        Stream<String> words = wordList.stream();
        wordList.add("zhaoliu");
        long count = words.count();
        System.out.println(count);
    }

    /**
     * ConcurrentModificationException
     * 不能在stream中修改 source
     */
    @Test
    public void testNointerference2() {
        List<String> wordList = new ArrayList<>();
        wordList.add("zhangsan");
        wordList.add("lisi");
        wordList.add("wangwu");
        wordList.stream().forEach(w -> wordList.remove(w));
    }

    /**
     * personList的stream再filter, 没有修改personList本身的数据
     */
    @Test
    public void testNoModify() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        List<Person> personList = Arrays.asList(zhangsan, lisi, wangwu);
        List<Person> ret = personList.stream().filter(p -> p.getNo() > 0).collect(Collectors.toList());
    }

    /**
     * 到了终结操作才会开始执行流
     */
    @Test
    public void testPeek1() {
        //什么都不显示
        Stream.of("one", "two", "three", "four").peek(System.out::println);
        //显示
        Stream.of("one", "two", "three", "four").peek(System.out::println).collect(Collectors.toList());
    }

    /**
     *
     * filtered value: three
     * mapped value: THREE
     * filtered value: four
     * mapped value: FOUR
     *
     */
    @Test
    public void testPeek2() {
        List<String> collect = Stream.of("one", "two", "three", "four")
            .filter(e -> e.length() > 3)
            .peek(e -> System.out.println("filtered value: " + e))
            .map(String::toUpperCase)
            .peek(e -> System.out.println("mapped value: " + e))
            .collect(Collectors.toList());
    }

    @Test
    public void testFindFirst() {
        Optional<String> first = Stream.of("one", "two", "three", "four")
            .filter(e -> e.length() > 3)
            .findFirst();
        System.out.println(first);
    }

    /**
     * stream().reduce()方法
     */
    @Test
    public void testReduce() {
        String reduce = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(reduce);
        Double reduce1 = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(reduce1);
        Integer reduce2 = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(reduce2);

        Optional<Integer> reduce3 = Stream.of(1, 2, 3, 4).reduce(Integer::sum);
        Integer r = reduce3.get();
        reduce3.ifPresent(System.out::println);
        System.out.println(r);
    }

    /**
     * limit()
     * skip()
     */
    @Test
    public void testLimitAndSkip1() {
        List<Person> persons = createPersonList1();
        List<String> personList2 = persons.stream()
                .map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * limit放在sorted操作后,是无法达到short-circuiting目的，
     * 原因跟 sorted 这个 intermediate 操作有关：此时系统并不知道 Stream 排序后的次序如何，
     * 所以 sorted 中的操作看上去就像完全没有被 limit 或者 skip 一样。
     *
     * peak:Person(no=4, name=name4, age=6, address=null, job=null)
     * Person(no=4, name=name4, age=6, address=null, job=null)
     * peak:Person(no=1, name=name1, age=10, address=null, job=null)
     * Person(no=1, name=name1, age=10, address=null, job=null)
     *
     */
    @Test
    public void testLimitAndSkip2() {
        List<Person> persons = createPersonList2();
        // persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(2).forEach(System.out::println);
        persons.stream().sorted(Comparator.comparingInt(Person::getAge)).peek(x -> System.out.println("peak:" + x)).limit(2).forEach(System.out::println);
    }

    /**
     * 和上面一个test进行比较,limit放在sorted前面
     *
     * Person(no=1, name=name1, age=10, address=null)
     * Person(no=2, name=name2, age=21, address=null)
     */
    @Test
    public void testLimitAndSkip3() {
        List<Person> persons = createPersonList2();
        List<Person> persons2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        System.out.println(persons2);
        // System.out.println(persons);
    }

    @Test
    public void testLimit4() {
        List<Person> persons = createPersonList2();
        List<Person> collect = persons.stream().filter(p -> p.getAge() > 0).limit(1).collect(Collectors.toList());
    }

    /**
     * 流不会改变源数据集合
     */
    @Test
    public void testSorted() {
        List<Integer> list = Arrays.asList(29, 5, 51, 2, 98, 21);
        List<Integer> collect1 = list.stream().limit(2).sorted((p1, p2) -> p2.compareTo(p1)).collect(Collectors.toList());
        System.out.println(collect1);
        //        List<Integer> collect2 = list.stream().sorted((p1, p2) -> p1.compareTo(p2)).limit(2).collect(Collectors.toList());
        List<Integer> collect2 = list.stream().sorted(Integer::compareTo).limit(2).collect(Collectors.toList());
        System.out.println(collect2);
        List<Integer> collect3 = list.stream().limit(2).sorted(Integer::compareTo).collect(Collectors.toList());
        System.out.println(collect3);
    }

    @Test
    public void testMatch() {
        List<Person> list = createPersonList2();
        boolean b1 = list.stream().allMatch(p -> p.getAge() < 1);
        System.out.println("allMatch: " + b1);

        boolean b2 = list.stream().anyMatch(p -> p.getAge() == 21);
        System.out.println("anyMatch: " + b2);

        boolean b3 = list.stream().noneMatch(p -> p.getAge() == 21);
        System.out.println("noneMatch: " + b3);

    }

    /**
     * Stream.generate(...)
     */
    @Test
    public void testSupplier1() {
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
    }

    /**
     * Stream.generate(...)
     */
    @Test
    public void testSupplier2() {
        Stream.generate(new PersonSupplier()).limit(10)
            .forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
    }

    /**
     * iterate 跟 reduce 操作很像
     */
    @Test
    public void testIterate() {
        Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);
    }

    /**
     * 可以设置分组类型(TreeMap)，和值的类型(ArrayList)
     *
     * Collectors.groupingBy(...)方法
     */
    @Test
    public void testGroupingBy1() {
        // Stream.generate，创建Stream
        TreeMap<Integer, ArrayList<Person>> collect = Stream.generate(new PersonSupplier()).limit(100)
            .collect(Collectors.groupingBy(Person::getAge, TreeMap::new, Collectors.toCollection(ArrayList::new)));
        Set<Map.Entry<Integer, ArrayList<Person>>> entrys = collect.entrySet();
        entrys.forEach(entry -> {
            Integer age = entry.getKey();
            List<Person> persons = entry.getValue();
            persons.forEach(p -> System.out.println("age:" + age + ", person:[name-" + p.getName() + ", age-" + p.getAge() + "]"));
            System.out.println();
        });
    }

    /**
     * Collectors.groupingBy(...)方法
     */
    @Test
    public void testGroupingBy2() {
        // Locale[] availableLocales = Locale.getAvailableLocales();

        Map<String, List<Locale>> countryToLocales =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(Locale::getCountry));
        List<Locale> locales = countryToLocales.get("CH");
    }

    /**
     * Collectors.groupingBy(...)方法
     */
    @Test
    public void testGroupingBy3() {
        Map<String, Set<Locale>> setMap =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));
    }

    // 不用lambda表达式，而是用的Function函数式接口
    // maxBy
    @Test
    public void testGroupingBy4() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        // wangwu和wangwu2的name属性重复了!!!
        Person wangwu2 = new Person(3, "wangwu", 40);

        Map<String, Optional<Person>> collect =
            Stream.of(zhangsan, lisi, wangwu, wangwu2)
                .collect(Collectors.groupingBy(Person::getName, Collectors.maxBy(Comparator.comparing(Person::getAge))));


        // Stream<Person> personStream = Stream.of(zhangsan, lisi, wangwu, wangwu2);

        // Map<String, Optional<Person>> collect = personStream.collect(Collectors.groupingBy(Person::getName, Collectors.maxBy(Comparator.comparing(new Function<Person, Integer>() {
        //     @Override
        //     public Integer apply(Person person) {
        //         return person.getAge();
        //     }
        // }))));
    }

    // 分组后，求count
    @Test
    public void testGroupingBy5() {
        Map<String, Long> collect =
            Stream.of(Locale.getAvailableLocales())
                .collect(Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
    }

    // groupingBy()中的downstream参数是Collector类型的，还可以继续分组
    @Test
    public void testGroupingBy6() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        Person zhaoliu = new Person(3, "zhaoliu", 30);
        Map<Integer, Map<String, List<Person>>> collect = Stream.of(zhangsan, lisi, wangwu, zhaoliu)
            .collect(Collectors.groupingBy(Person::getAge, Collectors.groupingBy(Person::getName)));
    }

    /**
     * Collectors.mapping(...) 方法可以在collect()方法之中，继续转换 stream 中数据的类型
     */
    @Test
    public void testGroupingBy7() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        Person zhaoliu = new Person(3, "zhaoliu", 30);
        Map<Integer, List<String>> collect = Stream.of(zhangsan, lisi, wangwu, zhaoliu)
            .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
    }

    @Test
    public void testGroupingBy8() {
        List<Person> persons = createPersonList2();
        Map<String, List<Person>> map = persons.stream()
            .collect(Collectors.groupingBy(person -> {
                if (person.getAge() < 20) return "YONG";
                else return "OLD";
            }));
    }

    @Test
    public void testGroupingBy9() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        Person zhaoliu = new Person(3, "zhaoliu", 30);
        Map<Integer, Integer> collect = Stream.of(zhangsan, lisi, wangwu, zhaoliu)
            .collect(Collectors.groupingBy(Person::getAge, Collectors.summingInt(Person::getAge)));
    }

    /**
     * Stream.iterate(...)
     */
    @Test
    public void testSummingInt() {
        int sum = Stream.iterate(0, i -> i + 1)
            .limit(10)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println(sum);
    }

    /**
     * 分区
     * 当分类函数是一个predicate函数(即返回一个"boolean类型"的函数)时，partitioningBy会比groupingBy更有效率
     */
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Locale>> en =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.partitioningBy(l -> l.getCountry().equals("en")));
    }

    /**
     * Collectors.toCollection(...)
     */
    @Test
    public void testToCollection() {
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        TreeSet<String> collect = list
            .stream().collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Collectors.joining(...)
     */
    @Test
    public void testJoin() {
        final String DELIMITER = "&";
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        String ret = list.stream().collect(Collectors.joining(DELIMITER));
        System.out.println(ret);
    }

    @Test
    public void testSplit() {
        final String DELIMITER = "&";
        String str = "zhangsan&lisi&wangwu";
        String[] split = str.split(DELIMITER);
        System.out.println(split);
    }

    /**
     * Collectors.toMap()
     */
    @Test
    public void testToMap() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        // wangwu和wangwu2的name属性重复了!!!
        Person wangwu2 = new Person(3, "wangwu", 40);

        List<Person> list = Arrays.asList(zhangsan, lisi, wangwu, wangwu2);

        TreeMap<String, Person> personMap =
            // list.stream().collect(Collectors.toMap(Person::getName, Function.identity()));  //error: Duplicate key...
            list.stream().collect(Collectors.toMap(Person::getName,
                Function.identity(),
                // 根据已有的值和新值来决定键的值，重写该行为
                (existingValue, newValue) -> newValue,
                TreeMap::new));
    }

    @Test
    public void tmp2() {
        Set<Integer> set = new HashSet<>();
        set.add(5);
        set.add(2);
        set.add(8);
        List<Integer> collect = set.stream().sorted(Integer::compare).collect(Collectors.toList());
        System.out.println();
    }

    /**
     * stream.flatMap(...)
     */
    @Test
    public void testFlatMap1() {
        Stream<List<Integer>> inputStream = Stream.of(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5, 6)
        );
        //        List<Integer> collect = inputStream.flatMap((childList) -> childList.stream()).collect(Collectors.toList());
        List<Integer> collect = inputStream.flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * flatMap()与map()方法的比较
     * flatMap()方法一定要生成一个Stream对象
     *
     */
    @Test
    public void testFlatMap2() {
        // 加入person1
        List<Person> persons = new ArrayList<>();
        Person person1 = new Person(1, "zhangsan", 20);
        person1.setAddress(Arrays.asList("shanghai", "beijing"));
        persons.add(person1);

        // 加入person2
        Person person2 = new Person(2, "lisi", 21);
        person2.setAddress(Arrays.asList("guangzhou", "shenzhen"));
        persons.add(person2);

        Stream<List<String>> listStream1 = persons.stream().map(p -> p.getAddress());
        List<List<String>> ret1 = listStream1.collect(Collectors.toList());

        Stream<Stream<String>> streamStream1 = persons.stream().map(p -> p.getAddress().stream());
        List<Stream<String>> ret2 = streamStream1.collect(Collectors.toList());

        // 注意：与stream比较，flatMap()方法生成的stream与父stream合并了,即：map方法产生了Stream<Stream<String>>，而flatMap产生了Stream<String>
        Stream<String> stringStream = persons.stream().flatMap(p -> p.getAddress().stream());
        List<String> ret3 = stringStream.collect(Collectors.toList());
        System.out.println(ret1);
        System.out.println(ret2);
        System.out.println(ret3);
    }

    /**
     * Stream转换成IntStream
     */
    @Test
    public void testMaptoInt1() {
        List<Person> person = createPersonList2();
        //intStream
        IntStream intStream = person.stream().mapToInt(Person::getAge);
        intStream.forEach(System.out::println);
    }

    /**
     * Stream转换成IntStream
     * stream.reduce(...)
     */
    @Test
    public void testMapToInt2() {
        List<Person> person = createPersonList2();
        OptionalInt reduce = person.stream().mapToInt(Person::getAge).reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }

    /**
     * 原始类型流与普通流的相互转换
     */
    @Test
    public void testIntStream() {
        List<Person> personList = createPersonList2();
        // 转换成IntStream
        IntStream intStream = personList
            .stream()
            .mapToInt(person -> person.getAge());
        // 转换成对象流
        Stream<Integer> boxed = intStream.boxed();
    }

    /**
     * stream().reduce(...)
     */
    @Test
    public void testIntBinaryOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Integer reduce = list.stream().reduce(0, (l, r) -> l + r);
        System.out.println(reduce);
    }

    /**
     * 两个Function用andThen()方法连接起来, g(f(x))
     *
     * 结果是4
     */
    @Test
    public void testFunction1() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = f.andThen(x -> x * 2);
        Integer ret = g.apply(1);
        System.out.println(ret);
    }

    /**
     * f(g(x))
     *
     * 结果是3
     */
    @Test
    public void testFunction2() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = f.compose(x -> x * 2);
        Integer ret = g.apply(1);
        System.out.println(ret);
    }

    /**
     * 流只能遍历一次
     */
    @Test
    public void test() {
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
        // error！流已经被消费掉了
        stream.forEach(System.out::println);
    }


    public List<Person> createPersonList2() {
        List<Person> list = Arrays.asList(
            new Person(1, "name1", 10),
            new Person(2, "name2", 21),
            new Person(3, "name3", 34),
            new Person(4, "name4", 6),
            new Person(5, "name5", 55));
        return list;
    }

    public ArrayList<Person> createPersonList1() {
        ArrayList<Person> persons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person(i, "name" + i, i);
            persons.add(person);
        }
        return persons;
    }

    /**
     * Collectors.summarizingInt(...)
     */
    @Test
    public void testSummarizingInt() {
        List<Person> persons = createPersonList2();
        IntSummaryStatistics summaryStatistics = persons
            .stream()
            .collect(Collectors.summarizingInt(Person::getAge));
        System.out.println(summaryStatistics);
    }

    /**
     *  Collectors.reducing(...)
     */
    @Test
    public void testReducing01() {
        List<Person> persons = createPersonList2();
        String s = persons
            .stream()
            .map(Person::getName)
            .collect(Collectors.reducing((s1, s2) -> s1 + ", " + s2))
            .get();
        System.out.println(s);
    }

    /**
     * stream.reduce(...)
     */
    @Test
    public void parallelRangedSum1() {
        long reduce = LongStream.rangeClosed(1, Integer.MAX_VALUE)
            .reduce(0L, Long::sum);
        System.out.println(reduce);
    }

    /**
     * Collectors.reducing(...)
     */
    @Test
    public void testReducing02() {
        List<Person> persons = createPersonList2();
        String collect = persons
            .stream()
            .collect(Collectors.reducing("", Person::getName, (s1, s2) -> (s1 + ", " + s2)));
        System.out.println(collect);

    }

    @Test
    public void testFilter() {
        String str = "abc";
        Optional<String> strOptional = Optional.ofNullable(str);
        strOptional.filter(s -> s.endsWith("c"))
            .ifPresent(System.out::println);
    }

    /**
     * lambda表达式异常处理
     * 没有抛出来
     */
    @Test
    public void testCatch() {
        Arrays.asList("1", "2", "3")
            .stream()
            .map(l -> {
                throw new NullPointerException();
            });
    }

    /**
     * random生成整数随机数流
     * limit短路
     */
    @Test
    public void testRandomAndLimit() {
        Random random = new Random();
        List<Integer> collect = random
            .ints(0, 10)
            .map(i -> {
                System.out.println(i);
                return i;
            })
            .distinct()
            .limit(5)
            // .distinct()
            .boxed()
            .collect(Collectors.toList());
    }

    @Test
    public void testNullable() {
        List<String> list = Collections.emptyList();
        List<String> collect = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public static class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();

        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
    }


}

