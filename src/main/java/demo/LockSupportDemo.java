package demo;


import org.junit.Test;

import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

/**
 * @author huishen
 * @date 2018/11/27 下午7:56
 * <p>
 * LockSupport很类似于二元信号量(只有1个许可证可供使用)
 * 许可证默认是被占用的
 * LockSupport是不可重入的
 */
public class LockSupportDemo {

    /**
     * 一直被阻塞
     */
    @Test
    public void test1() {
        LockSupport.park();
        System.out.println("block...");
    }

    /**
     * 先释放许可，再获取许可，程序能够正常终止
     */
    @Test
    public void test2() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        LockSupport.park();
        System.out.println("unblock");
        System.out.println("the interrupt is: " + Thread.currentThread().isInterrupted());
        System.out.println("the interrupt is: " + Thread.interrupted());
    }

    /**
     * 不可重入，会一直阻塞
     * 只会打印a和b，不会打印c
     */
    @Test
    public void test3() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        System.out.println("a");
        LockSupport.park();
        System.out.println("b");
        LockSupport.park();
        System.out.println("c");
    }

    /**
     * 中断线程, t的interrupt返回true，说明中断导致t被唤醒，但是不会重置interrupt，仍然为true，并且不会抛出InterruptedException
     */
    @Test
    public void test4() throws Exception {
        lockSupport((Thread t) -> t.interrupt());
    }

    /**
     * unpark方法唤醒线程t，interrupt返回false
     */
    @Test
    public void test5() throws Exception {
        lockSupport((Thread t) -> LockSupport.unpark(t));
    }


    @SuppressWarnings("all")
    private void lockSupport(Consumer<Thread> consumer) throws Exception {
        Thread t = new Thread(new Runnable() {
            private int count = 0;
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;
                while ((end - start) <= 1000) {
                    count++;
                    end = System.currentTimeMillis();
                }
                System.out.println("after 1 second.count=" + count);
                //等待或许许可
                LockSupport.park();
                System.out.println("thread over. interrupt: " + Thread.currentThread().isInterrupted());
                System.out.println("thread over. interrupt: " + Thread.interrupted());
            }
        });
        t.start();
        Thread.sleep(2000);

        consumer.accept(t);

        System.out.println("main over");
    }

}
