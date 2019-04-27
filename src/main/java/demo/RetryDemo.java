package demo;

import org.junit.Test;

/**
 * @author huishen
 * @date 18/5/25 下午10:14
 *
 * retry：需要放在for，whlie，do...while的前面声明，变量只跟在break和continue后面。
 *
 * retry只是类似于一个标记，这个标记随便叫什么都可以，不一定非要叫retry
 *
 */
public class RetryDemo {

    /**
     * continue retry    跳出这次循环，继续下次循环
     * 输出：0 1 2 4
     *
     */
    @Test
    public void test1() {
        retry:
        for (int i = 0; i < 5; i++) {
            while (i == 3) {
                continue retry;
            }
            System.out.print(i + " ");
        }
    }

    /**
     * break retry    直接终止外部的for循环，所以直接结束
     * 0 1 2
     */
    @Test
    public void test1_1() {
        retry:
        for (int i = 0; i < 5; i++) {
            while (i == 3) {
                break retry;
            }
            System.out.print(i + " ");
        }
    }

    /**
     * 死循环，因为会一直卡在 'i == 3' 这句话上。
     */
    @Test
    public void test2() {
        for (int i = 0; i < 5; i++) {
            retry:
            while (i == 3) {
                continue retry;
            }
            System.out.print(i + " ");
        }
    }

    /**
     * 0 1 2 3 4
     */
    @Test
    public void test2_1() {
        for (int i = 0; i < 5; i++) {
            retry:
            while (i == 3) {
                break retry;
            }
            System.out.print(i + " ");
        }
    }

    /**
     *
     */
    @Test
    public void test3() {
        int i = 0;
        // retry:  //①
        while (true) {
            i++;
            System.out.println("i=" + i);
            int j = 0;
            retry:   //②
            for (; ; ) {
                j++;
                System.out.println("j=" + j);
                if (j == 2) {
                    break retry;
                }
            }
        }
    }

}
