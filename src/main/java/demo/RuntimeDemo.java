package demo;

import org.junit.Test;

/**
 * @author huishen
 * @date 18/4/29 上午11:12
 */
public class RuntimeDemo {

    /**
     * 获得处理器个数
     */
    @Test
    public void test1() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("处理器个数:" + i);
    }

}
