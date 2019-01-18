package demo;

/**
 * @author huishen
 * @date 2019-01-02 16:26
 * <p>
 * java 位移运算与乘法运算
 */
public class ShiftDemo {

    public static void main(String[] args) {
        method1();
        method2();
    }

    public static void method1() {
        long start = System.nanoTime();
        int i = 100;
        int j = i * 2;
        System.out.println("乘法运算耗时:" + (System.nanoTime() - start));
    }

    public static void method2() {
        long start = System.nanoTime();
        int i = 100;
        int j = i << 1;
        System.out.println("位移运算耗时:" + (System.nanoTime() - start));

    }

}
