package threaddemo.ch02_05;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class Main {

    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        int count = 3;

        Thread[] thread = new Thread[count];
        for (int i = 0; i < count; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread" + i);
        }

        for (int i = 0; i < count; i++) {
            thread[i].start();
        }

    }

}
