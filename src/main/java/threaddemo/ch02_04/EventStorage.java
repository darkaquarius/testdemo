package threaddemo.ch02_04;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class EventStorage {

    private int maxSize;

    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() >= maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.println("Set: " + storage.size());
        notify();
    }

    public synchronized void get() {
        while(0 == storage.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Get: %d: %s",storage.size(),((LinkedList<?>)storage).poll());
            notify();
        }
    }

}
