package jvm;

/**
 * @author huishen
 * @date 18/4/1 下午7:56
 *
 * DCL(双锁检测)来实现单例
 *
 */
public class DCLSingleton {

    private volatile static DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (null == instance) {
            synchronized (DCLSingleton.class) {
                if (null == instance) {
                    instance = new DCLSingleton();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {
        DCLSingleton.getInstance();
    }

}
