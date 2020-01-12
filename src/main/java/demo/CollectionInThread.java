package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author huishen
 * @date 2019-07-31 10:00
 *
 * ArrayList, LinkedList, HashSet等数据结构，在多线程环境下的安全
 *
 */
public class CollectionInThread {

    private static final int N = 1_000;

    private List<Integer> arrayList = new ArrayList<>(N);
    private List<Integer> linkedList = new LinkedList<>();
    private Set<Integer> set = new HashSet<>(N);

    private List<Integer> cowArrayList = new CopyOnWriteArrayList<>();
    ConcurrentLinkedQueue<Integer> clq = new ConcurrentLinkedQueue<>();

    /**
     * ArrayList不是线程安全的
     */
    @Test
    public void test1() {
        doTest(arrayList);
    }

    /**
     * ArrayList不是线程安全的
     * 可以用Collections.synchronizedList()封装成线程安全的
     */
    @Test
    public void test2() {
        List<Integer> synchronizedList = Collections.synchronizedList(arrayList);
        doTest(synchronizedList);
    }

    /**
     * LinkedList不是线程安全的
     */
    @Test
    public void test3() {
        doTest(linkedList);
    }

    /**
     * 可以用Collections.synchronizedList()封装成线程安全的
     */
    @Test
    public void test4() {
        List<Integer> synchronizedList = Collections.synchronizedList(linkedList);
        doTest(synchronizedList);
    }

    /**
     * HashSet不是线程安全的
     */
    @Test
    public void test5() {
        doTest(set);
    }

    /**
     * 可以用Collections.synchronizedCollection()封装成线程安全的
     */
    @Test
    public void test6() {
        Collection<Integer> synchronizedSet = Collections.synchronizedCollection(set);
        doTest(synchronizedSet);
    }

    /**
     * CopyOnWriteArrayList是线程安全的
     */
    @Test
    public void test7() {
        doTest(cowArrayList);
    }

    /**
     * ConcurrentLinkedQueue是线程安全的
     */
    @Test
    public void test8() {
        doTest(clq);
    }

    private void doTest(Collection collection) {
        for (int i=0; i< N; i++) {
            final int ii = i;
            new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                collection.add(ii);
            }).start();
        }
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(collection.size() == N);
        System.out.println(collection.size());
    }

}
