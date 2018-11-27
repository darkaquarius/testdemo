package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Created by huishen on 16/10/21.
 *
 */
public class ArrayListTest {

    //测试Arrays.asList()的返回值是List还是ArrayList
    //返回值是List类型,此时不能用add()方法,会报错:UnsupportedOperationException
    @Test
    public void test1(){
        List<String> a = Arrays.asList("a");
        a.add("b");
        System.out.println(a);
    }

    @Test
    public void test2(){
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
    }

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
        for(String str : hset){
            System.out.println(str);
        }
    }

    // 初始化的时候，用Collections.EMPTY_LIST来避免空指针异常
    // 类似的还有：Collections.EMPTY_MAP, Collections.EMPTY_MAP
    @Test
    public void test4() {
        List list = Collections.EMPTY_LIST;
        System.out.println(list.size());
    }

    @Test
    public void test5() {
        List list = Arrays.asList(1, 2, 3);
        System.out.println(list.size());    // 3
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
            // list.remove(Integer.valueOf(i));
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
     *  测试Arrays.sort(array)
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
     *
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
        list.remove((Integer)20);
        list.add(20);
        System.out.println(list);
        list.remove((Integer)50);
        list.add(50);
        System.out.println(list);
    }

}
