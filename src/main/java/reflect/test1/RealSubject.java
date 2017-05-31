package reflect.test1;

/**
 * Created by huishen on 17/4/17.
 */
public class RealSubject implements Subject {

    @Override
    public String hello(String str) {
        String ret  = "hello ".concat(str);
        System.out.println(ret);
        return ret;
    }

}
