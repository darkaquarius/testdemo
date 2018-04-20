package jvm;

/**
 * @author huishen
 * @date 18/3/26 下午9:41
 *
 *
 */
public class ClassLoadingDemo {

    /**
     * 对于静态字段，只有直接定义该字段的类才会被初始化
     * 因此，通过子类来引用父类中的静态字段，只会触发父类的初始化而不会触发子类的初始化
     */
    // public static void main(String[] args) {
    //     System.out.println(SubClass.value);
    // }

    /**
     * 没有输出"SuperClass init!"
     * 没有触发"xx.xx.SuperClass"的初始化
     * 但是触发了"Lorg.xx.xx.SuperClass"的初始化
     * 这个类代表了SuperClass的一维数组
     */
    // public static void main(String[] args) {
    //     SuperClass[] sca = new SuperClass[10];
    // }

    /**
     * 常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类
     * 不会触发定义常量的类的初始化
     */
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOWORLD);
    }

}

class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}

class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}


class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLOWORLD = "hello world";
}