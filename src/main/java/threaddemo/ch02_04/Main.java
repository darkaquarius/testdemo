package threaddemo.ch02_04;

/**
 * Created by huishen on 17/6/28.
 *
 */
public class Main {

    public static void main(String[] args) {

        EventStorage storage = new EventStorage();

        new Thread(new Producer(storage)).start();
        new Thread(new Consumer(storage)).start();

    }


}
