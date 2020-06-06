package thread;

import java.util.concurrent.CountDownLatch;

public class SynchronizedDemo {

    public static class Record {
        private static Record instance = new Record();

        private Long startTime = -1L;

        private Record() {}

        public static Record getInstance() {
            return instance;
        }

//        public Long setStartTime() {
//            if (startTime == -1L) {
//                synchronized (startTime) {
//                    if (startTime == -1L) {
//                        startTime = System.currentTimeMillis();
//                    }
//                }
//            }
//
//            return startTime;
//        }

        /**
         * 锁住的是此对象的此方法
         */
        public synchronized Long setStartTime() {
            if (startTime == -1L) {
                synchronized (startTime) {
                    if (startTime == -1L) {
                        startTime = System.currentTimeMillis();
                    }
                }
            }

            return startTime;
        }

        /**
         * 当setStartTime()方法被锁住的时候，
         * 如果：
         * 1.getStartTime()是同步方法，阻塞，因为需要拿到锁
         * 2.getStartTime()是普通方法，不阻塞，仍然可以进入，因为不需要拿到锁就可以执行
         */
        public synchronized Long getStartTime() {
            return startTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Thread thread1 = new Thread(() -> {
            Record record = Record.getInstance();
            Long startTime = record.setStartTime();
            System.out.println(Thread.currentThread().getName() + ",startTime:" + startTime);
            countDownLatch.countDown();
        }, "Thread1");

        Thread thread2 = new Thread(() -> {
            Record record = Record.getInstance();
            Long startTime = record.setStartTime();
            System.out.println(Thread.currentThread().getName() + ",startTime:" + startTime);
            countDownLatch.countDown();
        }, "Thread2");


        Thread thread3 = new Thread(() -> {
            Record record = Record.getInstance();
            // 这里只会看startTime字段有没有上锁，没有上锁就可以进入代码块，而不需要关心record对象有没有上锁
            synchronized (record.getStartTime()) {
                Long startTime = record.getStartTime();
                System.out.println(Thread.currentThread().getName() + ",startTime:" + startTime);
            }
            countDownLatch.countDown();
        }, "Thread3");

        thread1.start();
        thread2.start();
        thread3.start();

        countDownLatch.await();
        System.out.println("main end");
    }

}
