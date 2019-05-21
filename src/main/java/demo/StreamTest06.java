package demo;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by huishen on 17/11/29.
 * 将数字按质数和非质数分区
 */
public class StreamTest06 {

    @Test
    public void test() {
        long s = System.currentTimeMillis();
        System.out.println(isPrime(49999));
        System.out.println("test spends: " + (System.currentTimeMillis() - s));
    }

    private boolean isPrime(int candicate) {
        boolean b = IntStream.rangeClosed(2, candicate)
            .noneMatch(i -> candicate % i == 0);

        return b;
    }

}
