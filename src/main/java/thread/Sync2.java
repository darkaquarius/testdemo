package thread;

/**
 * Created by huishen on 17/10/23.
 *
 */
public class Sync2 {

    public static synchronized void test() {
        System.out.println("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            MyThread2 thread = new MyThread2();
            thread.start();
        }
    }

}

class MyThread2 extends Thread {

    @Override
    public void run() {
        Sync2.test();
    }

}
