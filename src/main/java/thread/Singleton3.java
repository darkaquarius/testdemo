package thread;

/**
 * @author huishen
 * @date 2018/11/29 下午5:26
 * <p>
 * ThreadLocal
 */
public class Singleton3 {

    private static final ThreadLocal perThreadInstance = new ThreadLocal();
    private static Singleton3 singleton;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (perThreadInstance.get() == null) {
            // 每个线程第一次都会调用
            createInstance();
        }
        return singleton;
    }

    private static final void createInstance() {
        synchronized (Singleton3.class) {
            if (singleton == null) {
                singleton = new Singleton3();
            }
        }
        perThreadInstance.set(perThreadInstance);
    }
}
