package demo;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by huishen on 16/10/21.
 */
public class ArrayListTest {

    //测试Arrays.asList()的返回值是List还是ArrayList
    //返回值是List类型,此时不能用add()方法,会报错:UnsupportedOperationException
    @Test
    public void test1() {
        List<String> a = Arrays.asList("a");
        a.add("b");
        System.out.println(a);
    }

    /**
     * ArrayList允许存放null
     */
    @Test
    public void test2() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add("zhang");
        list.add("wang");
        list.add("zhao");
        list.add("li");
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
        System.out.println(list.get(4));
        System.out.println(list.get(5));
        System.out.println(list.get(6));  // IndexOutOfBoundsException
    }

    /**
     * HashSet允许存放null
     */
    @Test
    public void test3() {
        Set<String> hset = new HashSet<>();
        hset.add(null);
        hset.add(null);
        hset.add("zhang");
        hset.add("wang");
        hset.add("zhao");
        hset.add("li");
        System.out.println(hset);
        for (String str : hset) {
            System.out.println(str);
        }
    }

    /**
     * 初始化的时候，用Collections.EMPTY_LIST来避免空指针异常
     * 类似的还有：Collections.EMPTY_MAP, Collections.EMPTY_MAP
     */
    @Test
    public void test4() {
        List list = Collections.EMPTY_LIST;
        System.out.println(list.size());
    }

    // [-3, -2, -1] [-2, 0, 2]
    // list.remove(i)选择调用重载方法list.remove(int index), 从指定位置删除元素
    // 重载方法
    @Test
    public void test6() {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();

        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }

        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list.remove(i);
            // 应该改为
            list.remove(Integer.valueOf(i));
        }
        System.out.println(set + " " + list);
    }

    /**
     * 测试Collections.sort(List)
     */
    @Test
    public void testsort1() {
        List<String> list = new ArrayList<>();
        list.add("28");
        list.add("32");
        list.add("2");
        list.add("71");
        list.add("02");
        Collections.sort(list);
        list.forEach(System.out::println);
    }

    /**
     * 测试Arrays.sort(array)
     */
    @Test
    public void testsort2() {
        List<String> list = new ArrayList<>();
        list.add("28");
        list.add("32");
        list.add("2");
        list.add("71");
        list.add("02");
        String[] array = list.toArray(new String[]{});
        Arrays.sort(array);
        // 集合中的元素是否排序    集合没有排序
        System.out.println("items in list: ");
        list.forEach(str -> System.out.print(str.concat("\t")));
        // 数组中的元素是否排序    数组已经排序
        System.out.println("items in array: ");
        Stream.of(array).forEach(str -> System.out.print(str.concat("\t")));
    }

    /**
     * Collections.emptyList()建立的是AbstractList，不能添加元素
     * UnsupportedOperationException
     */
    @Test
    public void testEmpty() {
        List<String> list = Collections.emptyList();
        list.add("1");
        Arrays.toString(list.toArray());
    }

    @Test
    public void testRemove1() {
        ArrayList<String> list = new ArrayList<>(8);
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);
        list.remove("2");
        list.add("2");
        System.out.println(list);
        list.remove("5");
        list.add("5");
        System.out.println(list);
    }

    /**
     * remove()方法删除的是int类型的元素的时候，记得装箱
     * <p>
     * remove()有两种形式：
     * 1. public boolean remove(Object o)
     * 2. public E remove(int index)
     */
    @Test
    public void testRemove2() {
        ArrayList<Integer> list = new ArrayList<>(8);
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println(list);
        list.remove((Integer) 20);
        list.add(20);
        System.out.println(list);
    }

    @Test
    public void iterate() {
        List<Integer> collect = Stream.iterate(0, n -> n + 1)
            .limit(10)
            .collect(Collectors.toList());
    }

    /**
     * ArrayList#removeAll()好慢
     */
    @Test
    public void testRemoveAll() {
        List<String> bigList = new ArrayList<>();
        List<String> smallList = new ArrayList<>();

        for (int i = 0; i < 100_000; i++) {
            bigList.add(String.valueOf(i));
        }

        for (int i = 90_000; i > 0; i--) {
            smallList.add(String.valueOf(i));
        }

        System.out.println("a1：" + smallList.size());
        System.out.println("a2：" + bigList.size());

        long start = System.currentTimeMillis();
        bigList.removeAll(smallList);
        long end = System.currentTimeMillis();

        System.out.println(bigList.size());
        System.out.println("spend time：" + (end - start));
    }

    /**
     * LinkedList#removeAll()好慢
     */
    @Test
    public void testLinkedRemoveAll() {
        List<String> bigList = new LinkedList<>();
        List<String> smallList = new LinkedList<>();

        for (int i = 0; i < 100_000; i++) {
            bigList.add(String.valueOf(i));
        }

        for (int i = 90_000; i > 0; i--) {
            smallList.add(String.valueOf(i));
        }


        System.out.println("a1：" + smallList.size());
        System.out.println("a2：" + bigList.size());

        long start = System.currentTimeMillis();
        bigList.removeAll(smallList);
        long end = System.currentTimeMillis();

        System.out.println(bigList.size());
        System.out.println("spend time：" + (end - start));
    }

    /**
     * HashSet#removeAll()很快
     */
    @Test
    public void testHashSetRemoveAll() {
        HashSet<String> bigSet = new HashSet<>();
        HashSet<String> smallSet = new HashSet<>();

        for (int i = 0; i < 100_000; i++) {
            bigSet.add(String.valueOf(i));
        }

        for (int i = 90_000; i > 0; i--) {
            smallSet.add(String.valueOf(i));
        }


        System.out.println("a1：" + smallSet.size());
        System.out.println("a2：" + bigSet.size());

        long start = System.currentTimeMillis();
        bigSet.removeAll(smallSet);
        long end = System.currentTimeMillis();

        System.out.println(bigSet.size());
        System.out.println("spend time：" + (end - start));
    }

    @Test
    public void testArrayListAdd() {
        List<String> list = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            list.add(String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("spend time：" + (end - start));
    }

    @Test
    public void testLinkedListAdd() {
        List<String> list = new LinkedList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            list.add(String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("spend time：" + (end - start));
    }

    @Test
    public void testHashSetAdd() {
        Set<String> set = new HashSet<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            set.add(String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("spend time：" + (end - start));
    }

    /**
     * 在10的位置set()或者add()，都会IndexOutOfBoundsException
     */
    @Test
    public void testAdd() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);

        list.set(0, 1);
        list.add(0, 10);

        list.set(10, 10);
        list.add(10, 10);
    }

    @Test
    public void subList() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);

        List<Integer> subList = list.subList(1, 3);
        subList.add(0, 100);
    }

    @Test
    public void removeIf() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);

        list.removeIf(p -> p > 1);
    }

    /**
     * ArrayList的序列化
     * https://www.cnblogs.com/aoguren/p/4767309.html
     *
     * 由于 elementData 被transient修饰，原因：
     *     在new一个ArrayList对象时，默认开辟一个长度是10的对象数组，如果只存入几个对象，但是却采用默认的序列化方式，则会向其余为null也序列化到文件中
     *
     * 在序列化和反序列化过程中需要特殊处理的类必须使用下列准确签名来实现特殊方法：
     * private void writeObject(java.io.ObjectOutputStream out) throws IOException
     * private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
     * writeObject(ObjectOutputStream) 和readObject(ObjectInputStream)的使用方法参考:
     * {@link TestSerialization}
     *
     * readResolve()方法可以让反序列化返回同一个对象，而不是返回反射生成的新的对象。
     * readObject(ObjectInputStream s)方法可以对反射生成的新的对象中的属性进行修改。
     */
    @Test
    public void serializable() {

    }

}
