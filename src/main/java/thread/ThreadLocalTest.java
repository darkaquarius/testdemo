package thread;

/**
 * @author huishen
 * @date 2018/11/29 下午7:00
 */
public class ThreadLocalTest {

    /**
     * 设置默认值
     */
    private ThreadLocal<Long> longLocal = ThreadLocal.withInitial(() -> Thread.currentThread().getId());

    /**
     * 设置默认值
     */
    private ThreadLocal<String> stringLocal = ThreadLocal.withInitial(() -> Thread.currentThread().getName());

    public void set() {
        longLocal.set(Thread.currentThread().getId());
        stringLocal.set(Thread.currentThread().getName());
    }

    public long getLong() {
        return longLocal.get();
    }

    public String getString() {
        return stringLocal.get();
    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        final ThreadLocalTest test = new ThreadLocalTest();

        // test.set();
        System.out.println(test.getLong());
        System.out.println(test.getString());

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                // test.set();
                System.out.println(test.getLong());
                System.out.println(test.getString());
            }
        };
        thread1.start();
        thread1.join();

        System.out.println(test.getLong());
        System.out.println(test.getString());
    }

}
