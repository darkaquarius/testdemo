package reflect.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by huishen on 17/4/17.
 */
public class Client {

    public static void main(String args[]) {
        Subject realSubject = new RealSubject();

        InvocationHandler handler = new DynamicHandler(realSubject);

        Subject subject =
            (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), handler);

        System.out.println(subject.getClass().getName());

        String ret = subject.hello("world");

        System.out.println("ret is " + ret);

    }

}
