package threaddemo.ch01_08;

import java.util.Date;
import java.util.Deque;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class CleanerTask extends Thread {

    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }

    @Override
    public void run() {
        while(true) {
            Date date = new Date();
            clean(date);
        }
    }

    private void clean(Date date) {
        long difference;
        boolean delete;

        if (0 == deque.size()) {
            return;
        }

        delete = false;
        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if (difference > 5000) {
                System.out.printf("Cleaner: %s\n",e.getEvent());
                deque.removeLast();
                delete = true;
            }
        } while (difference > 10000);

        if (delete) {
            System.out.println("Cleaner: Size of the queue: " + deque.size());
        }

    }

}
