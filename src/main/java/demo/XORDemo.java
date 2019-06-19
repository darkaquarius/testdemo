package demo;

import org.junit.Test;

/**
 * @author huishen
 * @date 2019-06-18 22:48
 */
public class XORDemo {

    /**
     * 异或
     * a ^ a = 0
     */
    @Test
    public void test1() {
        int i = 100;
        i = i ^ i;
        System.out.println(i);
    }

    /**
     * a ^ 0 =  a;
     */
    @Test
    public void test3() {
        int i = 100;
        i = i ^ 0;
        System.out.println(i);
    }

}
