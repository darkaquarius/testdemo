package demo;

import org.springframework.util.Assert;

/**
 * Created by huishen on 17/5/10.
 */
public class AssertDemo {

    public void testNotNull(String str) {
        Assert.notNull(str, "str must not be null");
        System.out.println("yes");
    }

    public static void main(String[] args) {
        new AssertDemo().testNotNull(null);
    }

}
