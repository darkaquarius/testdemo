package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
