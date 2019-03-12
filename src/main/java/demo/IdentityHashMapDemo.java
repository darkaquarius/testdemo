package demo;

import org.junit.Test;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author huishen
 * @date 2019-02-26 15:10
 */
@SuppressWarnings("Duplicates")
public class IdentityHashMapDemo {

    /**
     * 判断key是否相等的时候，用的是引用相等，即 "=="，而不是"equals()"
     */
    @Test
    public void test1() {
        IdentityHashMap<String, Object> map = new IdentityHashMap<>();
        map.put(new String("xx"), "first");
        map.put(new String("xx"), "second");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " ");
            System.out.println(entry.getValue());
        }
        System.out.println("idenMap=" + map.containsKey("xx"));
        System.out.println("idenMap=" + map.get("xx"));
    }

    @Test
    public void test2() {
        IdentityHashMap<String, Object> map = new IdentityHashMap<>();
        String fsString = new String("xx");
        map.put(fsString, "first");
        map.put(new String("xx"), "second");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " ");
            System.out.println(entry.getValue());
        }
        System.out.println("idenMap=" + map.containsKey(fsString));
        System.out.println("idenMap=" + map.get(fsString));
    }

    @Test
    public void test3() {
        IdentityHashMap<String, Object> map = new IdentityHashMap<>();
        String fsString = new String("xx");
        String secString = new String("xx");
        map.put(fsString, "first");
        map.put(secString, "second");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + " ");
            System.out.println(entry.getValue());
        }
        System.out.println("idenMap=" + map.containsKey(fsString));
        System.out.println("idenMap=" + map.get(fsString));
        System.out.println("idenMap=" + map.containsKey(secString));
        System.out.println("idenMap=" + map.get(secString));
    }

}
