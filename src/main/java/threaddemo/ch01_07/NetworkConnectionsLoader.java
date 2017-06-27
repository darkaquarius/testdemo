package threaddemo.ch01_07;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class NetworkConnectionsLoader implements Runnable {

    @Override
    public void run() {
        System.out.println("Begining network connections loading: " + new Date());
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Network connections loading has finished: " + new Date());
    }
}
