package demo;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by huishen on 17/11/8.
 *
 */

public class TimerDemo {

    @Test
    public void test1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start");
            }
        }, 1000, 2000);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    }

}
