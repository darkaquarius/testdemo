package threaddemo.ch01_12;

import java.util.Random;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class Task implements Runnable {

    @Override
    public void run() {
        int result;

        Random random = new Random(Thread.currentThread().getId());

        while(true) {
            result = 1000 / 0;
            System.out.printf("%s: %f", Thread.currentThread().getId(), result);

            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("%d : Interrupted\n",Thread.currentThread().getId());
                return;
            }
        }


    }
}
