package threaddemo.ch01_10;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class SafeTask implements Runnable {

    private static ThreadLocal<Date> startDate= new ThreadLocal<Date>() {
        protected Date initialValue(){
            return new Date();
        }
    };

    @Override
    public void run() {
        // 注意是"startDate.get()"，不是"startDate"
        System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(), startDate.get());
        try {
            TimeUnit.SECONDS.sleep((int)Math.rint(Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(), startDate.get());
    }
}
