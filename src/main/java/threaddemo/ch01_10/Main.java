package threaddemo.ch01_10;

import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class Main {

    public static void main(String[] args) {
        // UnsafeTask task = new UnsafeTask();
        SafeTask task = new SafeTask();

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(task);
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
