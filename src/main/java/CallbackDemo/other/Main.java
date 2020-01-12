package CallbackDemo.other;

/**
 * @author huishen
 * @date 2019-06-19 11:38
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Thread: " + Thread.currentThread().getName() + ", Func func start");
        new Func().func("我是测试数据", new Func.Callback() {
            @Override
            public void onSucceed(String data) {
                System.out.println("Thread: " + Thread.currentThread().getName() + ", onSucceed, return data: " + data);
            }
        });
        System.out.println("Func func end");
    }

}
