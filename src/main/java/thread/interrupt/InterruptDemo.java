package thread.interrupt;

import org.junit.Test;

/**
 * @author huishen
 * @date 2019-06-20 10:40
 *
 * https://zhuanlan.zhihu.com/p/45667127
 *
 */
public class InterruptDemo {

    /**
     * 中断失败
     * 程序中并没有响应中断信号的逻辑，所以程序不会有任何反应
     */
    @Test
    public void test1() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();
            }
        });
        thread.start();
        thread.interrupt();

    }

    /**
     * 中断成功
     * 有响应中断的逻辑，程序接收到中断信号打印出信息后返回退出
     */
    @Test
    public void test2() {
        Thread thread = new Thread(() -> {
            while (true) {
                Thread.yield();

                // 响应中断
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Java技术栈线程被中断，程序退出。");
                    return;
                }
            }
        });
        thread.start();
        thread.interrupt();
    }

    /**
     * 中断失败
     * sleep() 方法被中断后会"清除中断标记"，所以循环会继续运行。。
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Java技术栈线程休眠被中断，程序退出。");
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }

    /**
     * 中断成功
     * 在 sleep() 方法被中断并清除标记后手动重新中断当前线程，然后程序接收中断信号返回退出。
     *
     * @throws InterruptedException
     */
    @Test
    public void test4() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Java技术栈线程休眠被中断，程序退出。");
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }



}
