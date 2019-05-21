package dynamicProxy;

/**
 * @author huishen
 * @date 2019-04-27 09:04
 */
public class HelloWorldImpl implements HelloWorld {

    @Override
    public void sayHello(String str) {
        System.out.println("hello world " + str);
    }

    @Override
    public void sayHello2(String str) {
        System.out.println("hello world2 " + str);
    }
}
