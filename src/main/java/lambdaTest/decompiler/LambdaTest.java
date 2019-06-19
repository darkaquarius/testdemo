package lambdaTest.decompiler;

/**
 * @author huishen
 * @date 2019-05-22 14:54
 * <p>
 * 反编译后，lambda表达式等价的执行代码
 *
 * https://www.jianshu.com/p/57bffc6e7acd
 * https://www.cnblogs.com/WJ5888/p/4667086.html
 */
public class LambdaTest {

    public LambdaTest() {
    }

    public static void main(String[] args) {
        Func add = new LambdaTest$$Lambda$1();
        System.out.println(add.exec(1, 2));
    }

    private static int lambda$main$0(int x, int y) {
        return x + y;
    }

    /**
     * 这里是静态内部类，因为反编译：javap -p 'LambdaTest$$Lambda$1.class'之后：
     *
     * 内部类中没有外部类的引用，构造函数也是无参的。
     *
     * final class LambdaTest$$Lambda$1 implements Func {
     *   private LambdaTest$$Lambda$1();
     *   public int exec(int, int);
     * }
     *
     */
    static final class LambdaTest$$Lambda$1 implements Func {

        private LambdaTest$$Lambda$1() {
        }

        @Override
        public int exec(int x, int y) {
            return LambdaTest.lambda$main$0(x, y);
        }
    }

}

@FunctionalInterface
interface Func {
    int exec(int x, int y);
}