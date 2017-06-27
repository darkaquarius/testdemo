package threaddemo.ch01_06;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class FileClock implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // 这里应该释放或者关闭线程正在使用过的资源
                System.out.printf("The FileClock has been interrupted");
            }
        }
    }
}
