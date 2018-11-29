package thread;

/**
 * @author huishen
 * @date 2018/11/29 下午5:47
 * <p>
 * 使用内部类实现延迟加载
 */
public class Singleton4 {

    private Singleton4() {
    }

    public static class Holder {
        // 这里的私有没有什么意义
        /* private */static Singleton4 instance = new Singleton4();
    }

    public static Singleton4 getInstance() {
        // 外围类能直接访问内部类（不管是否是静态的）的私有变量
        return Holder.instance;
    }

}
