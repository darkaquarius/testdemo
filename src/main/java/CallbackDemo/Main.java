package CallbackDemo;

/**
 * @author huishen
 * @date 2019-06-19 10:12
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        client.send("我是测试数据", new MyCallback() {
            @Override
            public void onCompletion(Object o, Exception e) {
                if (o != null) {
                    System.out.println("callback Thread: " + Thread.currentThread().getName() + " " + o);
                } else {
                    System.out.println("callback Thread: " + Thread.currentThread().getName() + " " + e);
                }
            }
        });

        System.out.println("client send finished");
        Thread.sleep(10_000);
    }

}
