package demo;

import org.junit.Test;

import java.util.Map;
import java.util.Properties;

/**
 * Created by huishen on 17/4/27.
 */
public class SystemDemo {

    @Test
    public void testGetenv() {
        Map<String, String> getenv = System.getenv();
    }

    @Test
    public void test() {
        Properties properties = System.getProperties();
    }

    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        System.out.println(l);

    }

}
