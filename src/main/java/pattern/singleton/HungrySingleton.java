package pattern.singleton;

public class HungrySingleton {
//    方式1
//    private static HungrySingleton instance = new HungrySingleton();

//    方式2
    private static HungrySingleton instance = null;
    static {
        instance = new HungrySingleton();
    }

    private HungrySingleton() {
    }

    public static HungrySingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        HungrySingleton instance = HungrySingleton.getInstance();
        HungrySingleton instance1 = HungrySingleton.getInstance();
        HungrySingleton instance2 = HungrySingleton.getInstance();
    }

}
