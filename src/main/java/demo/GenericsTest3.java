package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huishen
 * @date 18/2/2 下午8:16
 *
 * 传递泛型
 *
 */
public class GenericsTest3 {

    @Test
    public void test1() {
        GenericsTest3 instance = new GenericsTest3();
        List<String> strings = instance.<String>func1();
        strings.add("1");
    }

    private <V> List<V> func1() {
        List<V> list = new ArrayList<>();
        return list;
    }


}
