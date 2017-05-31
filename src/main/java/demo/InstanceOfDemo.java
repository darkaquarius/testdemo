package demo;

import org.junit.Test;

/**
 * Created by huishen on 16/12/31.
 */
public class InstanceOfDemo {

    @Test
    public void test(){
        Father father = new Father();
        Child child = new Child();
        GrandChild grandChild = new GrandChild();
        System.out.println(father instanceof Father);
        System.out.println(child instanceof Father);
        System.out.println(grandChild instanceof Father);
        System.out.println(father instanceof Child);
        System.out.println(child instanceof Child);
        System.out.println("--------------------");
        System.out.println(father.getClass());
        System.out.println(child.getClass());
        System.out.println(grandChild.getClass());
    }
}

class Father{

}

class Child extends Father{

}

class GrandChild extends Child{

}