package demo;


import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

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

    @SuppressWarnings("all")
    @Test
    public void test4() throws Exception {
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
                System.out.println("thread over." + Thread.currentThread().isInterrupted());
            }
        });
        t.start();
        Thread.sleep(2000);
        // 中断线程
        t.interrupt();
        System.out.println("main over");
    }

}
