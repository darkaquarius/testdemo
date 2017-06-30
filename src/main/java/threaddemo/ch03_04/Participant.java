package threaddemo.ch03_04;

/**
 * Created by huishen on 17/6/30.
 *
 */
public class Participant implements Runnable {

    private Videoconference videoconference;

    private String name;

    public Participant(Videoconference videoconference, String name) {
        this.videoconference = videoconference;
        this.name = name;
    }

    @Override
    public void run() {
        joinIn();
    }

    // 加入会议
    public void joinIn() {
        try {
            Thread.sleep((int) (Math.random() * 10_000 * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        videoconference.getCountDownLatch().countDown();
        System.out.println(this.name + " join in the videoconference");
    }
}
