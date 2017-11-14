package demo;

import org.junit.Test;

import java.util.Date;

/**
 * Created by huishen on 17/11/14.
 *
 */

public class DateDemo {

    /**
     * 测试new Date(long date)方法
     * 建立的Date对象可以精确到秒
     */
    @Test
    public void test1() {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

}
