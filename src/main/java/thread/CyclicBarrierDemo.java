package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author huishen
 * @date 2018-12-04 17:10
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        int N = 3;
        CyclicBarrier barrier = new CyclicBarrier(N, () -> {
            System.out.println("当前线程" + Thread.currentThread().getName());
        });

        for (int i = 0; i < N; i++) {
            new Writer(barrier).start();
            Thread.sleep(2000);
        }

        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n\nCyclicBarrier重用");

        for (int i = 0; i < N; i++) {
            new Writer(barrier).start();
            Thread.sleep(1000);
        }
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "已经到达" + System.currentTimeMillis());
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() + "开始处理" + System.currentTimeMillis());
        }
    }
}
