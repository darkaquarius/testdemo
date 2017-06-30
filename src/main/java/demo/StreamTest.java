package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by huishen on 16/9/29.
 * StreamTest
 */
public class StreamTest {

    // 不干扰，这里正常
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

    @Test
    public void testNointerference2() {
        List<String> wordList = new ArrayList<>();
        wordList.add("zhangsan");
        wordList.add("lisi");
        wordList.add("wangwu");
        wordList.stream().forEach(w -> wordList.remove(w));
    }

    // personList的stream再filter, 没有修改personList本身的数据
    @Test
    public void testNoModify() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        List<Person> personList = Arrays.asList(zhangsan, lisi, wangwu);
        List<Person> ret = personList.stream().filter(p -> p.getNo() > 0).collect(Collectors.toList());
    }

    @Test
    public void testPeek1() {
        //什么都不显示
        Stream.of("one", "two", "three", "four").peek(System.out::println);
        //显示
        Stream.of("one", "two", "three", "four").peek(System.out::println).collect(Collectors.toList());
    }

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
            .filter(e -> e.length() > 10)
            .findFirst();
        System.out.println(first);
    }

    @Test
    public void testOptional1() {
        String text = "Hello World";
        //         Pre-Java 8
        //        if (text != null) {
        //            System.out.println("Pre-java 8: text = " + text);
        //        }
        Optional<String> text1 = Optional.ofNullable(text);

        //        ifPresent
        //        text1.ifPresent(System.out::println);
        text1.ifPresent(e -> {
            System.out.println(e);
            System.out.println(e);
        });

    }

    @Test
    public void testOptional2() {
        //        Java 8
        Integer integer = Optional.ofNullable("Hello World").map(String::length).orElse(-1);
        System.out.println(integer);
        //        Pre-Java 8
        //        if (text != null) ? text.length() : -1;
    }

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

    @Test
    public void testReduce2() {
        List<Integer> list = Arrays.asList(29, 5, 51, 2, 98, 21);
        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }

    @Test
    public void testLimitAndSkip1() {
        List<Person> persons = createPersonList1();
        List<String> personList2 = persons.stream().
            map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }

    /**
     * limit放在sorted操作后,是无法达到short-circuiting目的，
     * 原因跟 sorted 这个 intermediate 操作有关：此时系统并不知道 Stream 排序后的次序如何，
     * 所以 sorted 中的操作看上去就像完全没有被 limit 或者 skip 一样。
     */
    @Test
    public void testLimitAndSkip2() {
        List<Person> persons = createPersonList1();
        //        persons.stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).limit(2).forEach(System.out::println);
        persons.stream().sorted(Comparator.comparing(Person::getAge)).limit(2).forEach(System.out::println);
    }

    /**
     * 和上面一个test进行比较,limit放在sorted前面
     */
    @Test
    public void testLimitAndSkip3() {
        List<Person> persons = createPersonList1();
        List<Person> persons2 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        System.out.println(persons2);
        System.out.println(persons);
    }

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

        boolean b2 = list.stream().anyMatch(p -> p.getAge() == 22);
        System.out.println("anyMatch: " + b2);

        boolean b3 = list.stream().noneMatch(p -> p.getAge() == 21);
        System.out.println("noneMatch: " + b3);

    }

    @Test
    public void testSupplier1() {
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
    }

    @Test
    public void testSupplier2() {
        Stream.generate(new PersonSupplier()).limit(10)
            .forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
    }

    //iterate 跟 reduce 操作很像
    @Test
    public void testIterate() {
        Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);
    }

    // 可以设置分组类型(TreeMap)，和值的类型(ArrayList)
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

    @Test
    public void testGroupingBy2() {
        // Locale[] availableLocales = Locale.getAvailableLocales();

        Map<String, List<Locale>> countryToLocales =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(Locale::getCountry));
        List<Locale> locales = countryToLocales.get("CH");
    }

    // 对map中的值进行处理，不再是List，而是Set(downstream)
    // 还有一些：counting, maxBy, summing
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

        // Map<String, Optional<Person>> collect =
        //     Stream.of(zhangsan, lisi, wangwu, wangwu2).collect(Collectors.groupingBy(Person::getName, Collectors.maxBy(Comparator.comparing(Person::getAge))));


        Stream<Person> personStream = Stream.of(zhangsan, lisi, wangwu, wangwu2);

        Map<String, Optional<Person>> collect = personStream.collect(Collectors.groupingBy(Person::getName, Collectors.maxBy(Comparator.comparing(new Function<Person, Integer>() {
            @Override
            public Integer apply(Person person) {
                return person.getAge();
            }
        }))));
    }

    // 分组后，求count
    @Test
    public void testGroupingBy5() {
        Map<String, Long> collect =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
    }

    // groupingBy()中的downstream参数是Collector类型的，还可以继续分组
    @Test
    public void testGroupingBy6() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        Person zhaoliu = new Person(3, "zhaoliu", 30);
        Map<Integer, Map<String, List<Person>>> collect = Stream.of(zhangsan, lisi, wangwu, zhaoliu).collect(Collectors.groupingBy(Person::getAge, Collectors.groupingBy(Person::getName)));
    }

    // mapping
    @Test
    public void testGroupingBy7() {
        Person zhangsan = new Person(0, "zhangsan", 10);
        Person lisi = new Person(1, "lisi", 20);
        Person wangwu = new Person(2, "wangwu", 30);
        Person zhaoliu = new Person(3, "zhaoliu", 30);
        Map<Integer, List<String>> collect = Stream.of(zhangsan, lisi, wangwu, zhaoliu).collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));
    }

    // 当分类函数是一个predicate函数(即返回一个"boolean类型"的函数)时，partitioningBy会比groupingBy更有效率
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Locale>> en =
            Stream.of(Locale.getAvailableLocales()).collect(Collectors.partitioningBy(l -> l.getCountry().equals("en")));
    }

    @Test
    public void testToCollection() {
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        TreeSet<String> collect = list.stream().collect(Collectors.toCollection(TreeSet::new));
    }

    @Test
    public void testJoin() {
        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        String ret = list.stream().collect(Collectors.joining(","));
        System.out.println(ret);
    }

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
    public void testFlatMap1() {
        Stream<List<Integer>> inputStream = Stream.of(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5, 6)
        );
        //        List<Integer> collect = inputStream.flatMap((childList) -> childList.stream()).collect(Collectors.toList());
        List<Integer> collect = inputStream.flatMap(List::stream).collect(Collectors.toList());
    }

    @Test
    public void testFlatMap2() {
        List<Person> persons = new ArrayList<>();
        Person person1 = new Person(1, "zhangsan", 20);
        person1.setAddress(Arrays.asList("shanghai", "beijing"));
        persons.add(person1);

        Person person2 = new Person(2, "lisi", 21);
        person2.setAddress(Arrays.asList("guangzhou", "shenzhen"));
        persons.add(person2);

        List<String> collect = persons.stream().flatMap(p -> p.getAddress().stream()).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void testMaptoInt1() {
        List<Person> person = createPersonList2();
        //intStream
        IntStream intStream = person.stream().mapToInt(Person::getAge);
        intStream.forEach(System.out::println);
    }

    @Test
    public void testMapToInt2() {
        List<Person> person = createPersonList2();
        OptionalInt reduce = person.stream().mapToInt(Person::getAge).reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }

    @Test
    public void testIntBinaryOperator() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Integer reduce = list.stream().reduce(0, (l, r) -> l + r);
        System.out.println(reduce);
    }

    public List<Person> createPersonList2() {
        List<Person> list = Arrays.asList(new Person(1, "name1", 10),
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

}

class PersonSupplier implements Supplier<Person> {
    private int index = 0;
    private Random random = new Random();

    @Override
    public Person get() {
        return new Person(index++, "StormTestUser" + index, random.nextInt(100));
    }
}


