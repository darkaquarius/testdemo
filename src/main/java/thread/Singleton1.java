package thread;

/**
 * @author huishen
 * @date 2018/11/29 下午4:47
 * <p>
 * 饿汉式
 */
public class Singleton1 {

    private static Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }

}
