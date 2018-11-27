package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Integer --> double
     */
    @Test
    public void test5() {
        Integer number = 14924;
        double d = (double) number;
        System.out.println(number);
    }

    // 排名变化
    @Test
    public void test6() {
        // 前
        Map<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("抖音", 0);
        map1.put("爱奇艺", 999);

        // 后
        Map<String, Integer> map2 = new LinkedHashMap<>();
        map2.put("爱奇艺", 0);
        map2.put("抖音", 999);

        Integer index1 = map1.get("爱奇艺");
        Integer index2 = map2.get("爱奇艺");

        int i = index1 - index2;
        System.out.println(i);
    }

    @Test
    public void test7() {
        int i1 = 9111;
        int i2 = 10000;
        int ret = (int)((float)i1 / i2 * 100);
        System.out.println(ret);
    }

}
