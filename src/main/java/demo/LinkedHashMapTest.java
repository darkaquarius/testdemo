package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author huishen
 * @date 2019-07-31 18:07
 */
public class LinkedHashMapTest {

    @Test
    public void test0() {
        HashMap<String, String> map = new LinkedHashMap<>(4);
        map.put("k1s", "v1");
        map.put("k2f", "v2");
        map.put("k3v", "v3");
        map.put("k4b", "v4");
        map.put("k5b", "v5");
        map.put("k6f", "v6");
        map.put("k7g", "v7");
        map.put("k8q", "v8");
        map.put("k9a", "v9");
        map.put("k10p", "v10");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }
}
