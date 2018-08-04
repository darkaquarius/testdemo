package demo;

import org.junit.Test;

/**
 * @author huishen
 * @date 18/5/25 下午10:14
 */
public class RetryDemo {

    @Test
    public void test1() {
        retry:
        for (int i = 0; i < 10; i++) {
            // retry:
            while (i == 3) {
                continue retry;
            }
            System.out.print(i + " ");
        }
    }

}
