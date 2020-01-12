package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author huishen
 * @date 2019-07-29 16:27
 *
 * 所有的子线程用await()等待同时开始，main线程等待所有子线程执行结束再结束。
 */

public class CountDownLatchDemo3 {

    public static void main(String[] args) throws InterruptedException {

        final int N = 3;

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ", start");
                try {
                    // 为了让所有线程同时开始任务，我们让所有线程先阻塞在这里
                    // 等大家都准备好了，再打开这个门栓
                    startSignal.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                work();
                System.out.println(Thread.currentThread().getName() + ", end");
                doneSignal.countDown();
            }).start();
        }

        doSomethingElse();
        System.out.println(Thread.currentThread().getName() + ", startSignal countDown");
        // 因为这里 startSignal 是1，所以，只要调用一次，那么所有的 await 方法都可以通过
        startSignal.countDown();
        // 等待所有任务结束
        doneSignal.await();
        System.out.println(Thread.currentThread().getName() + ", end");
    }

    public static void doSomethingElse() {
        System.out.println(Thread.currentThread().getName() + ", doSomethingElse start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", doSomethingElse end");
    }

    public static void work() {
        System.out.println(Thread.currentThread().getName() + ", work start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ", work end");
    }

}
