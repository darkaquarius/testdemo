package thread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huishen
 * @date 2018/11/30 上午9:55
 */
public class ReentrantLockDemo {

    private ArrayList<Integer> arrayList = new ArrayList<>();

    private Lock lock = new ReentrantLock();

    /**
     * 主线程必须用yield()方法等待用户线程，不然直接结束了
     */
    @Test
    @SuppressWarnings("all")
    public void testLock() {
        final ReentrantLockDemo demo = new ReentrantLockDemo();
        new Thread(() -> demo.insertByLock(Thread.currentThread())).start();
        new Thread(() -> demo.insertByLock(Thread.currentThread())).start();
        new Thread(() -> demo.insertByLock(Thread.currentThread())).start();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    @Test
    @SuppressWarnings("all")
    public void testTryLock() {
        final ReentrantLockDemo demo = new ReentrantLockDemo();
        new Thread(() -> demo.insertByTryLock(Thread.currentThread())).start();
        new Thread(() -> demo.insertByTryLock(Thread.currentThread())).start();
        new Thread(() -> demo.insertByTryLock(Thread.currentThread())).start();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    @Test
    @SuppressWarnings("all")
    public void testLockInterruptibly() {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        Thread thread1 = new Thread(() -> {
            try {
                demo.insertByLockInterruptibly(Thread.currentThread());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被中断");
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                demo.insertByLockInterruptibly(Thread.currentThread());
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "被中断");
            }
        });
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    /**
     * lock.lock()
     */
    private void insertByLock(Thread thread) {
        lock.lock();
        try {
            System.out.println(thread.getName() + ":得到了锁");
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }
            Thread.sleep(1_000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(thread.getName() + ":释放了锁");
            // arrayList.forEach(i -> System.out.print( "i: " + i + "\t"));
            System.out.println();
            //  unlock()方法通常放在finally块中，因为出错时候不会自动释放锁，造成死循环。
            lock.unlock();
        }
    }

    /**
     * lock.tryLock()
     */
    private void insertByTryLock(Thread thread) {
        if (lock.tryLock()) {
            try {
                System.out.println(thread.getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName() + "获取锁失败");
        }
    }

    /**
     * lock.lockInterruptibly()
     *
     * @throws InterruptedException
     */
    private void insertByLockInterruptibly(Thread thread) throws InterruptedException {
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {
            System.out.println(thread.getName() + "得到了锁");
            long startTime = System.currentTimeMillis();
            for (; ; ) {
                if (System.currentTimeMillis() - startTime >= 5_000) {
                    break;
                }
                //插入数据
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "执行finally");
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁");
        }
    }

}
