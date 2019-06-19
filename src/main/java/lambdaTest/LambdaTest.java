package lambdaTest;

/**
 * @author huishen
 * @date 2019-05-22 14:53
 * <p>
 * 正常的lambda表达式的例子
 * <p>
 * https://www.jianshu.com/p/57bffc6e7acd
 * https://www.cnblogs.com/WJ5888/p/4667086.htmls
 */
public class LambdaTest {
    public static void main(String[] args) {
        Func add = (x, y) -> x + y;

        for (int i = 0; i < 10; i++) {
            System.out.println("sum: " + add.exec(1, 2));
        }
    }
}

interface Func {
    int exec(int x, int y);
}
