package thread;

/**
 * Created by huishen on 17/3/13.
 *
 */
public class Sync2 implements Runnable{

    @Override
    public void run() {
        test();
    }

    public synchronized void test(){
        System.out.println("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    public static void main(String[] args){
        // Sync2 sync2 = new Sync2();
        for (int i = 0; i < 3; i++) {
            Sync2 sync2 = new Sync2();
            new Thread(sync2).start();
        }
    }

}
