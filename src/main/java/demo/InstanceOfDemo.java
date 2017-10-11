package demo;

import org.junit.Test;

/**
 * Created by huishen on 16/12/31.
 *
 */

public class InstanceOfDemo {

    @Test
    public void test() {
        Father father = new Father();
        Child child = new Child();
        GrandChild grandChild = new GrandChild();
        System.out.println(father instanceof Father);   // true
        System.out.println(child instanceof Father);    // true
        System.out.println(grandChild instanceof Father);   // true
        System.out.println(father instanceof Child);    // false
        System.out.println(child instanceof Child);     // true
        System.out.println("--------------------");
        System.out.println(father.getClass());
        System.out.println(child.getClass());
        System.out.println(grandChild.getClass());
    }
}

class Father {

}

class Child extends Father {

}

class GrandChild extends Child {

}