package thread;

/**
 * Created by huishen on 17/10/23.
 *
 */

public class Sync4 implements Runnable {

    @Override
    public void run() {
        test();
    }

    public void test() {
        synchronized (Sync4.class) {
            System.out.println("start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end");
        }
    }

    public static void main(String[] args) {
        Sync4 sync4 = new Sync4();
        for (int i = 0; i < 3; i++) {
            // Sync4 sync4 = new Sync4();
            new Thread(sync4).start();
        }
    }

}
