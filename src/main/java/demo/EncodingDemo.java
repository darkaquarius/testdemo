package demo;

import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by huishen on 17/8/25.
 *
 */

public class EncodingDemo {

    @Test
    public void test01() {
        System.out.println("当前JRE：" + System.getProperty("java.version"));
        // 此方法使用平台默认的字符集来获取字符串对应的字节数组
        System.out.println("当前JVM的默认字符集：" + Charset.defaultCharset());
    }

}
