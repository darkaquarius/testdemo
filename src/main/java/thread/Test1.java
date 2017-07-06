package thread;

/**
 * Created by huishen on 17/3/2.
 *
 */
public class Test1 {
    public volatile int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) {
        final Test1 test = new Test1();
        for(int i=0;i<500;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                };
            }.start();
        }

        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        // inc的值总是小于500000,最根本的原因是"inc++"不是原子操作
        System.out.println(test.inc);
    }
}
