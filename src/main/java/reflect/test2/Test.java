package reflect.test2;

/**
 * Created by huishen on 17/4/18.
 */
public class Test {

    public static void main(String[] args) {

        HelloWorldService helloWorldService = (HelloWorldService) RPCProxyClient.getProxy(new HelloWorldServiceImpl());

        helloWorldService.sayHello("test");

    }

}
