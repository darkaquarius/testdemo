package thread;

/**
 * Created by huishen on 17/11/3.
 *
 */

public class ThreadDemo {

    public static void main(String[] args) {

    }

}

class MyThread01 implements Runnable {
    @Override
    public void run() {
        System.out.println("thread-start");
        for (int i = 0; i < 1000000; i++) {
            for (int j = 0; j < 1000000; j++) {

            }
        }
    }
}
