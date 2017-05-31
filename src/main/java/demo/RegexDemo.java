package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/5/25.
 *
 */
public class RegexDemo {

    //判断手机号是否合法
    @Test
    public void test() {
        String regex = "^1\\d{10}$";

        if (!"13888888888".matches(regex)) {
            System.out.println("false");
        } else {
            System.out.println("true");
        }
    }

}
