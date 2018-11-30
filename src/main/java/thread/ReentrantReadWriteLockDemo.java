package thread;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author huishen
 * @date 2018/11/30 下午3:26
 */

@SuppressWarnings("all")
public class ReentrantReadWriteLockDemo {

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 用synchronized，2个线程不能同时进行读操作
     */
    @Test
    public void test1() {
        final ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        new Thread(() -> demo.getBySync(Thread.currentThread())).start();
        new Thread(() -> demo.getBySync(Thread.currentThread())).start();

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    /**
     * 如果有一个线程已经占用了读锁，则此时其他线程还可以申请读锁
     * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁
     */
    @Test
    public void test2() {
        final ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        new Thread(() -> demo.getByReadLock(Thread.currentThread())).start();
        new Thread(() -> demo.getByReadLock(Thread.currentThread())).start();
        new Thread(() -> demo.getByWriteLock(Thread.currentThread())).start();


        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    /**
     * 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁
     */
    @Test
    public void test3() {
        final ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();

        new Thread(() -> demo.getByWriteLock(Thread.currentThread())).start();
        new Thread(() -> demo.getByReadLock(Thread.currentThread())).start();
        new Thread(() -> demo.getByWriteLock(Thread.currentThread())).start();


        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("end");
    }

    /**
     * synchronized
     */
    private synchronized void getBySync(Thread thread) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start <= 1000) {
            System.out.println(thread.getName() + "正在进行读操作");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(thread.getName() + "读操作完毕");
    }

    /**
     * ReentrantReadWriteLock的读锁
     */
    private void getByReadLock(Thread thread) {
        rwl.readLock().lock();
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= 1000) {
                System.out.println(thread.getName() + "正在进行读操作");
                Thread.sleep(50);
            }
            System.out.println(thread.getName() + "读操作完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.readLock().unlock();
        }
    }

    /**
     * ReentrantReadWriteLock的写锁
     */
    private void getByWriteLock(Thread thread) {
        rwl.writeLock().lock();
        try {
            long start = System.currentTimeMillis();
            while(System.currentTimeMillis() - start <= 1000) {
                System.out.println(thread.getName() + "正在进行写操作");
                Thread.sleep(50);
            }
            System.out.println(thread.getName() + "写操作完毕");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwl.writeLock().unlock();
        }
    }


}
