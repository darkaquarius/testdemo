package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/6/6.
 *
 */
public class ClassDemo {

    @Test
    public void test() {
        String name = String.class.getName();
        System.out.println(name);
    }

    @Test
    public void test2() {
        System.out.println(NullPointerException.class.isInstance(new Exception())); //false
        // true, 父类.class.isInstance(子类)
        System.out.println(Exception.class.isInstance(new NullPointerException()));
        System.out.println(NullPointerException.class.isInstance(new NullPointerException()));  // true
    }

}
