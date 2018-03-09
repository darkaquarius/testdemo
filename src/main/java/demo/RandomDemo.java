package demo;

import org.junit.Test;

/**
 * Created by huishen on 18/1/6.
 *
 */

public class RandomDemo {

    /**
     * 生成随机6位数
     */
    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println((int)((Math.random() * 9 + 1) * 100000));
        }
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
        }

    }
}
