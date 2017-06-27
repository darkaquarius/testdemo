package threaddemo.ch01_07;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class DataSourcesLoader implements Runnable {

    @Override
    public void run() {
        System.out.println("Begining data sources loading: " + new Date());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Data sources loading has finished: " + new Date());
    }

}
