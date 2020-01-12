package demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huishen on 16/12/31.
 *
 */

public class InstanceOfDemo {

    @Test
    public void test01() {
        Father father = new Father();
        Child child = new Child();
        System.out.println(child.getClass().equals(Child.class));
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

    @Test
    public void test02() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        List<Object> list = Arrays.asList(list1, list2);
        for (Object o : list) {
            List object = (List) o;
            Object o1 = object.get(0);
            if (o1 instanceof String) {
                System.out.println("String");
            } else if (o1 instanceof Integer) {
                System.out.println("Integer");
            } else{

            }
        }
    }

}

class Father {

}

class Child extends Father {

}

class GrandChild extends Child {

}