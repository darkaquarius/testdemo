package reflect.test2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huishen on 17/4/18.
 */
public class RPCProxyClient implements InvocationHandler {

    private Object obj;

    public RPCProxyClient(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method");

        String ret = (String) method.invoke(obj, args);

        System.out.println("after method");

        return ret;
    }

    public static Object getProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new RPCProxyClient(obj));
    }
}
