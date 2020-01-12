package pattern.singleton;

/**
 * @author huishen
 * @date 2019-08-01 10:02
 *
 * 用静态内部类实现单例
 */
public class StaticInnerClassSingleton {

    private static class Inner {
        private final static StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return Inner.instance;
    }

    private StaticInnerClassSingleton() {
        if(Inner.instance != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }

    public static void main(String[] args) {
        StaticInnerClassSingleton instance = StaticInnerClassSingleton.getInstance();
        System.out.println(instance);
        StaticInnerClassSingleton instance1 = StaticInnerClassSingleton.getInstance();
        System.out.println(instance1);
    }

}
