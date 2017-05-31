package reflect.test3;


/**
 * Created by huishen on 17/4/30.
 */
public class Test {

    public static void main(String[] args) {
        HelloWorldService helloWorldService = (HelloWorldService) ProxyClient.getInstance(new HelloWorldServiceImpl());
        int i = helloWorldService.sayHello(1);
        System.out.println(i);
    }

}
