package reflect.test3;

/**
 * Created by huishen on 17/4/30.
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public int sayHello(int param) {
        System.out.println("sayHello-start");
        System.out.println("sayHello-param:" + param);
        System.out.println("sayHello-end");
        return param;
    }
}
