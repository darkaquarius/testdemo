package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by huishen on 17/6/15.
 *
 */
public class HashMapDemo {

    private HashMap<Integer, Integer> map = new HashMap<>();

    @SuppressWarnings("all")
    public HashMapDemo() {

        // Thread t1 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.put(i, i);
        //     }
        //     System.out.println("t1 over");
        // });
        //
        // Thread t2 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.put(i, i);
        //     }
        //
        //     System.out.println("t2 over");
        // });
        //
        // Thread t3 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.put(i, i);
        //     }
        //
        //     System.out.println("t3 over");
        // });
        //
        // Thread t4 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.put(i, i);
        //     }
        //
        //     System.out.println("t4 over");
        // });
        //
        // Thread t5 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.put(i, i);
        //     }
        //
        //     System.out.println("t5 over");
        // });
        //
        // Thread t6 = new Thread(() -> {
        //     for (int i = 0; i < 5000000; i++) {
        //         map.get(i);
        //     }
        //
        //     System.out.println("t6 over");
        // });
        //
        // t1.start();
        // t2.start();
        // t3.start();
        // t4.start();
        // t5.start();
        //
        // t6.start();
    }

    public static void main(String[] args) {
        new HashMapDemo();
    }

    /**
     * 每次调用keySet()方法，返回的都是同一个实例
     */
    @Test
    public void test() {
        HashMap<String, String> map = new HashMap<>(2);
        map.put("key1", "value1");
        Set<String> keySet1 = map.keySet();
        map.put("key2", "value2");
        Set<String> keySet2 = map.keySet();
    }

    @Test
    public void test1() {
        HashMap<String, String> map = new HashMap<String, String>() {
            {
                put("k1", "v1");
                put("k2", "v2");
                put("k3", "v3");
            }
        };

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals("v1")) {
                map.remove(entry.getKey());
            }

        }
    }

    @Test
    public void test2() {
        HashMap<String, String> map = new HashMap<String, String>() {
            {
                put("k1", "v1");
                put("k2", "v2");
                put("k3", "v3");
            }
        };

        String str = map.get("k4");
        System.out.println(str);
    }

    @Test
    @SuppressWarnings("all")
    public void test3() {
        final HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                map.put(UUID.randomUUID().toString(), "");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println(map);
    }

}
