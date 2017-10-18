package demo;

/**
 * Created by huishen on 17/10/11.
 * 重载
 * 如果重载的两个方法的参数有继承关系，则会根据具体调用情况，选择某一个方法
 */

public class OverloadDemo {

    public void function(String str) {
        System.out.println("function String");
    }

    public void function(Object object) {
        System.out.println("funciton Object");
    }

    public static void main(String[] args) {
        OverloadDemo demo01 = new OverloadDemo();
        demo01.function("a");
        demo01.function(1);
    }

}
