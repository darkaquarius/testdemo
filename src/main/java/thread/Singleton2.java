package thread;

/**
 * Created by huishen on 17/7/1.
 * <p>
 * double check
 */
public class Singleton2 {

    // double-check模式时，使用"volatile"改进!!!
    private volatile static Singleton2 instance = null;

    private Singleton2() {
    }

    // double-check
    public static Singleton2 getInstance() {
        if (null == instance) {
            synchronized (Singleton2.class) {
                if (null == instance) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}