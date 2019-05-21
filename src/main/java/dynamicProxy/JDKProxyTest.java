package dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * @author huishen
 * @date 2019-04-27 09:23
 */
public class JDKProxyTest {

    /**
     * Proxy
     */
    public static void main(String[] args) {
        HelloWorld helloWorld = (HelloWorld) Proxy.newProxyInstance(JDKProxyTest.class.getClassLoader(), new Class[]{HelloWorld.class}, new MyInvocationHandler(new HelloWorldImpl()));
        helloWorld.sayHello("shen");
        helloWorld.sayHello2("shen");
    }

}
