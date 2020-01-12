package demo;

/**
 * @author huishen
 * @date 2019-08-01 11:31
 * <p>
 * enum的高级写法
 */
public enum EnumInstance {
    A,
    B("B") {
        @Override
        public String func() {
            return "B func";
        }

        /**
         * 这个方法不能被外部访问到，即使声明了public
         * 如果想被访问到，必须在EnumInstance中也声明一个func2()放法
         */
        public String func2() {
            return "B func2";
        }

    },
    C,
    D;

    // 类变量，可以直接通过类名调用
    private static int value = 10;

    public static int getValue() {
        return value;
    }

    // 实例变量
    String type = "EnumInstance的实例变量";

    public void setType(String type) {
        this.type = type;
    }

    EnumInstance() {
    }

    EnumInstance(String type) {
        this.type = type;
    }

    /**
     * 实例方法，在A, B, C, D中可以直接用，也可以进行覆写该方法。
     */
    public String func() {
        return "EnumInstance func";
    }


    public static void main(String[] args) {
        // 类变量，可以直接通过类名调用
        int value = EnumInstance.getValue();

        EnumInstance a = EnumInstance.A;
        System.out.println("A func: " + a.func());

        EnumInstance b = EnumInstance.B;
        System.out.println("B func: " + b.func());
        // 编译出错，访问不到 func2()
        // System.out.println("B func2: " + b.func2());


    }


}
