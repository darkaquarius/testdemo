package demo;

import java.util.HashMap;

/**
 * Created by huishen on 17/6/15.
 *
 */
public class HashMapDemo {

    private HashMap<Integer, Integer> map = new HashMap<>();

    @SuppressWarnings("all")
    public HashMapDemo() {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.put(i, i);
            }
            System.out.println("t1 over");
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.put(i, i);
            }

            System.out.println("t2 over");
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.put(i, i);
            }

            System.out.println("t3 over");
        });

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.put(i, i);
            }

            System.out.println("t4 over");
        });

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.put(i, i);
            }

            System.out.println("t5 over");
        });

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 5000000; i++) {
                map.get(i);
            }

            System.out.println("t6 over");
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        t6.start();
    }

    public static void main(String[] args) {
        new HashMapDemo();
    }

    public void testArrayListPair() {
        // TODO: 17/10/21 Chapter05 Line300
        // List<Pair>>
    }

}
