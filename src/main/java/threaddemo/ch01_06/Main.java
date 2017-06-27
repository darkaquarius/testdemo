package threaddemo.ch01_06;

import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread(new FileClock());
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }

}
