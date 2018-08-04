package demo;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author huishen
 * @date 18/4/30 下午8:32
 */
public class UnaryOperatorDemo {

    /**
     * 责任链模式
     */
    @Test
    public void test() {
        UnaryOperator<String> func1 = (str) -> "hello " + str;
        UnaryOperator<String> func2 = (str) -> str.replaceAll("world", "java");
        // 注意andThen()和compose()方法的区别
        Function<String, String> func = func1.andThen(func2);
        String ret = func.apply("world");
        System.out.println(ret);
    }

}
