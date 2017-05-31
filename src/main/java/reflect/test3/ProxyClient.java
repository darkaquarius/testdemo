package reflect.test3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huishen on 17/4/30.
 */
public class ProxyClient implements InvocationHandler {
    private Object object;

    public ProxyClient(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ProxyClient-invoke-start");
        Object invoke = method.invoke(object, args);
        System.out.println("ProxyClient-invoke-end");
        return invoke;
    }

    public static Object getInstance(Object object) {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new ProxyClient(object));
    }
}
