package java8inactiondemo;

import org.junit.Test;

import java.io.File;

/**
 * Created by huishen on 17/7/10.
 *
 */
public class Demo01 {

    @Test
    public void test1() {
        new File(".").listFiles(File::isHidden);
    }

    @Test
    public void test2() {
    }

}
