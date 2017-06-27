package threaddemo.ch01_04;

/**
 * Created by huishen on 17/6/27.
 *
 */
public class Main {

    public static void main(String[] args) {
        Thread task = new PrimeGenerator();
        task.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task.interrupt();
    }

}
