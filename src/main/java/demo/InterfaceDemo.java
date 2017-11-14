package demo;

/**
 * Created by huishen on 17/11/9.
 *
 * SonInterface继承于ParentInterface，有func1()方法
 * SonClass同样继承于ParentInterface，实现了func1()方法
 * 用SonInterface去调用func1()方法，
 * 用GrandSonClass继承于SonInterface
 *
 */
public class InterfaceDemo {

    public static void main(String[] args) {
        SonInterface sonInterface = new GrandSonClass();
        sonInterface.func1();
    }

}

interface ParentInterface {
    void func1();
}

interface SonInterface extends ParentInterface {

}

class SonClass implements ParentInterface {
    @Override
    public void func1() {
        System.out.println("func1");
    }
}

class GrandSonClass implements SonInterface {
    @Override
    public void func1() {
        System.out.println("func1-1");
    }
}
