package demo;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author huishen
 * @date 2018/11/16 下午5:53
 */
public class TimeUnitDemo {

    /**
     * toMillis    纳秒转微秒
     */
    @Test
    public void test1() throws InterruptedException {
        long start = System.nanoTime();
        Thread.sleep(500);
        long end = System.nanoTime() - start;
        long peroid1 = end / 1_000_000;
        System.out.println(peroid1);
        long peroid2 = TimeUnit.NANOSECONDS.toMillis(end);
        System.out.println(peroid2);
    }

}