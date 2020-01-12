package thread;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueDemo {

    @Test
    public void testSize() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
        queue.offer("A");
        queue.offer("B");
        int size = queue.size();
        System.out.println("queue size is " + size);
    }
}
