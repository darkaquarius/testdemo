package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huishen on 17/6/6.
 *
 */
public class CollectionsDemo {

    @Test
    public void testUnmodifiableSet() {
        Set<String> names = new HashSet<>();
        names.add("zhangsan");
        names.add("lisi");
        names.add("wangwu");

        //unmodifiableSet
        Set<String> ret = Collections.unmodifiableSet(names);

        names.add("baichi");

        // java.lang.UnsupportedOperationException
        // 这里用UnmodifiableSet类型，也引用了names对象
        // UnmodifiableSet类型的add()方法会报错
        // 但是，可以通过更改names，来更改ret，因为它们引用的是用一个对象
        // ret.add("baichi,too");

        for (String str : ret) {
            System.out.println(str);
        }
    }

    @Test
    public void testBinarySearch() {
        List<String> list = new ArrayList<>();
        list.add("v");
        list.add("p");
        list.add("q");
        list.add("g");
        list.add("g");
        list.add("a");
        // step1: sort排序
        Collections.sort(list);
        list.stream().forEach(str -> System.out.print(str.concat("\t")));
        System.out.println();

        // step2: 二分查找
        int p = Collections.binarySearch(list, "g");
        System.out.println(p);
    }

    /**
     * shuffle, 随机
     */
    @Test
    public void test() {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(10);
        list.add(2);
        list.add(11);
        list.add(79);
        Collections.shuffle(list);
        list.forEach(l -> System.out.print(l + "\t"));

    }

}
