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
    public void testAvailableProcessors() {
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("处理器个数:" + i);
    }

    /**
     * 注册JVM关闭钩子
     */
    @Test
    public void testAddShutdownHook() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("now stoping");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("register a shutdown hook, now stoped");
            }
        });
    }

}
