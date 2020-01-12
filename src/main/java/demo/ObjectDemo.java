package demo;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

/**
 * @author huishen
 * @date 18/5/13 下午12:40
 */
public class ObjectDemo {

    @Test
    public void test1() {
        String str1 = ObjectUtils.defaultIfNull("str", "empty");
        System.out.println("str1: " + str1);

        String str2 = ObjectUtils.defaultIfNull(null, "empty");
        System.out.println("str2: " + str2);
    }

    @Test
    public void test2() {
        Object[] objects = new Object[128];
        // 数组中创建出来了128个null
        System.out.println(objects[0]);
        System.out.println(objects[1]);
    }

}
