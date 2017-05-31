package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/5/24.
 *
 */
public class IntegerDemo {

    @Test
    public void test() {
        long s = System.currentTimeMillis();

        // Long sum = 0L;
        long sum = 0L;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }

        System.out.println(sum);
        System.out.println("Spend time: " + (System.currentTimeMillis() - s));
    }

}
