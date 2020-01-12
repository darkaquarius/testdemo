package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @author huishen
 * @date 2019-07-31 17:44
 */
public class HashSetDemo {

    @Test
    public void test0() {
        HashSet<String> set = new HashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("wangwu");
    }

    @Test
    public void test1() {
        HashSet<String> set = new LinkedHashSet<>();
        set.add("zhangsan");
        set.add("lisi");
        set.add("wangwu");
    }

}
