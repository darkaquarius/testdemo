package jvm;

/**
 * @author huishen
 * @date 18/3/22 下午3:16
 *
 * Jconsole监控代码：线程监控
 * 死锁演示代码
 *
 *
 *
 */
public class JconsoleDemo03 {

    /**
     * 线程死锁演示
     */
    static class SynAddRunnable implements Runnable {
        int a, b;
        public SynAddRunnable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            // 造成死锁的原因是Integer.valueOf()方法基于减少对象创建次数和节省内存的考虑，[-128, 127]之间的数字会被缓存
            // 当valueOf()方法传入参数在这个范围之内，将直接返回缓存中的对象
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunnable(1, 2)).start();
            new Thread(new SynAddRunnable(2, 1)).start();
        }
    }

}
