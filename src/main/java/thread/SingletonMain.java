package thread;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author huishen
 * @date 2018/11/29 下午5:07
 */
public class SingletonMain {

    /**
     * 饿汉式
     */
    @Test
    public void testSington1() {
        testSingeton(Singleton1::getInstance);
    }

    /**
     * double check
     */
    @Test
    public void testSingleton2() {
        testSingeton(Singleton2::getInstance);
    }

    /**
     * ThreadLocal
     */
    @Test
    public void testSingleton3() {
        testSingeton(Singleton3::getInstance);
    }

    @Test
    public void testSingleton4() {
        testSingeton(Singleton4::getInstance);
    }

    private void testSingeton(Supplier supplier) {
        for (int i = 0; i < 1000; i++) {
            Object singleton = supplier.get();
            System.out.println(String.valueOf(i).concat(" ").concat(singleton.toString()));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
