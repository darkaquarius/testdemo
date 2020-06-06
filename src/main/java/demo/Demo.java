package demo;

import org.junit.Test;

/**
 * Created by huishen on 16/11/25.
 */

public class Demo {

    @Test
    public void test1() {
        try {
            int ret = test2();
            System.out.println("result is:" + ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int test2() {
        int ret;
        try {
            ret = 10 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ret;
    }

}
