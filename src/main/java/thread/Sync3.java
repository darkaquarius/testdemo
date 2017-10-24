package thread;

/**
 * Created by huishen on 17/3/13.
 * 1.sleep()只会让出cpu，不会让出"该对象"的同步锁
 * 2.synchronized锁住的是对象，不是代码块
 */
public class Sync3 implements Runnable {

    @Override
    public void run() {
        test();
    }

    public synchronized void test() {
        System.out.println("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    public static void main(String[] args) {
        // Sync3 sync3 = new Sync3();
        for (int i = 0; i < 3; i++) {
            Sync3 sync3 = new Sync3();
            new Thread(sync3).start();
        }
    }

}
