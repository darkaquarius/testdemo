package pattern.singleton;

/**
 * @author huishen
 * @date 2019-08-01 10:10
 *
 * 用enum实现单例
 *
 *
 * see: {@link demo.EnumInstance}
 *
 */
public enum EnumSingleton {
    INSTANCE {
        // 经过jad的反编译，是用的匿名类去覆写print()方法
        @Override
        public void print() {
            System.out.println("print");
        }
    };

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

    public abstract void print();

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
