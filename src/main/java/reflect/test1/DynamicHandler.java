package reflect.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by huishen on 17/4/17.
 */
public class DynamicHandler implements InvocationHandler {

    public Object subject;

    public DynamicHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before real method");

        String ret = (String) method.invoke(subject, args);

        System.out.println("after real method");

        return ret;
    }
}
