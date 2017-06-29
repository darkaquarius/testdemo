package threaddemo.ch03_01;

/**
 * Created by huishen on 17/6/28.
 *
 */

public class Main {

    public static void main(String args[]) {

        // Creates the print queue
        PrintQueue printQueue = new PrintQueue();

        // Creates ten Threads
        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread " + i);
        }

        // Starts the Threads
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }

}
