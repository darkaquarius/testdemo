package guavademo;

import com.google.common.collect.EvictingQueue;
import org.junit.Test;

import java.util.Collections;

/**
 * EvictingQueue不是线程安全的，也不接受null。
 */
public class EvictingQueueDemo {

    @Test
    public void test01() {
        EvictingQueue<Integer> queue = EvictingQueue.create(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);
        queue.add(7);
        queue.add(8);
        queue.add(9);
        queue.add(10);
        Integer max = Collections.max(queue);
        System.out.println(max);
    }



}
