package thread;

/**
 * Created by huishen on 17/7/1.
 *
 * double check 模式
 */
public class Singleton {

    // double-check模式时，使用"volatile"改进!!!
    private volatile static Singleton instance = null;

    private Singleton() {}

    // double-check
    public static Singleton getInstance() {
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance)
                    instance = new Singleton();
            }
        }
        return instance;
    }

}

class Main {

    public static void main(String[] args) {
        for (int i= 0; i < 1000; i++) {
            Singleton singleton = Singleton.getInstance();
            System.out.println(String.valueOf(i).concat(" ").concat(singleton.toString()));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
