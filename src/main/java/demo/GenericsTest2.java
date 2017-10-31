package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class GenericsTest2 {

    @Test
    public void test1() {
        List<Number> list = new ArrayList<>();
        list.add(1);
    }

    /**
     * List<Object>可以add List<String>, List<Integer>......
     * List<?> 只能add null
     * List<Integer> 只能add int类型
     */
    @Test
    public void test2() {
        List<Object> list1 = new ArrayList<>();
        list1.add(1);
        list1.add("str");
        list1.add(true);

        List<? extends Integer> list2 = new ArrayList<>();
        // list2.add(1);  // error!
        list1.add(null);

        List<Integer> list3 = new ArrayList<>();
        list3.add(1);

    }

    @Test
    public void test3() {
        // List<Object> list = new ArrayList<Integer>();    // error!
        List<Object> list1 = new ArrayList<Object>();

        List<? extends Object> list2 = new ArrayList<Integer>();
    }


}
