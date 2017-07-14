package thread;

/**
 * Created by huishen on 17/3/4.
 * synchronize锁住的是代码块还是对象
 * 是对象！！！不是代码块
 * static synchronized 全局锁
 */
public class Sync {

    // http://blog.csdn.net/xiao__gui/article/details/8188833
    // synchronized(this)以及非static的synchronized方法（至于static synchronized方法请往下看），
    // 只能防止多个线程同时执行同一个对象的同步代码段。

    // synchronized锁住的是括号里的对象，而不是代码

    // 如果真的想锁住这段代码，要怎么做？
    // 比较多的做法是让synchronized锁这个类对应的Class对象
    // static synchronized方法也相当于全局锁，相当于锁住了代码段


    public static synchronized void test(){
        System.out.println("start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    public static void main(String args[]){
        // Sync sync = new Sync();
        for (int i = 0; i < 3; i++){
            Sync sync = new Sync();
            new MyThread(sync).start();
        }
    }

}

class MyThread extends Thread {

    private Sync sync;

    public MyThread(Sync sync){
        this.sync = sync;
    }

    @Override
    public void run(){
        sync.test();
    }

}
