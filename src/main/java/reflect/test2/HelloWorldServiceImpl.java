package reflect.test2;

/**
 * Created by huishen on 17/4/18.
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello(String msg) {
        String ret = "hello world " + msg;
        System.out.println(ret);
        return ret;
    }
}
