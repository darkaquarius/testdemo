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

    @Test
    public void test2() {
        long num = 1_0_0_0;
        while (num > 0) {
            System.out.println(num);
            num--;
        }
    }

    @Test
    public void test3() {
        long maxLong = Long.MAX_VALUE;
        maxLong += 1;
        // -9223372036854775808
        System.out.println(maxLong);
    }

    @Test
    public void test4() {
        int maxInteger = Integer.MAX_VALUE;
        maxInteger += 1;
        // -2147483648
        System.out.println(maxInteger);
    }

}
