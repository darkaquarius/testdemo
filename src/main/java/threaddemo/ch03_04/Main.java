package threaddemo.ch03_04;

/**
 * Created by huishen on 17/6/30.
 *
 */
public class Main {

    public static void main(String[] args) {
        int threadNum = 30;

        Videoconference videoconference = new Videoconference(threadNum);
        new Thread(videoconference).start();

        for (int i = 0; i < threadNum; i++) {
            new Thread(new Participant(videoconference, "Participant" + i)).start();
        }

    }

}
