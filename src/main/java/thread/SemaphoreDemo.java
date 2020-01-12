package thread;

import java.util.concurrent.Semaphore;

/**
 * @author huishen
 * @date 2018-12-04 18:06
 * <p>
 * 信号量
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        final int N = 8;
        final int S = 5;

        Semaphore semaphore = new Semaphore(S);
        for (int i = 0; i < N; i++) {
            int ii = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("工人" + ii + "占用一个机器在生产...");
                    Thread.sleep(2000);
                    System.out.println("工人" + ii + "释放出机器");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
