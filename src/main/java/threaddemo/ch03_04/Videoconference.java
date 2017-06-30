package threaddemo.ch03_04;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**
 * Created by huishen on 17/6/30.
 *
 */

@Data
public class Videoconference implements Runnable {

    private CountDownLatch countDownLatch;

    public Videoconference(int threadNum) {
        this.countDownLatch = new CountDownLatch(threadNum);
    }

    @Override
    public void run() {
        System.out.println("waiting for 5 Participants...");
        await();
        System.out.println("5 Participants, Videoconference starts");
    }

    // 等开会
    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
