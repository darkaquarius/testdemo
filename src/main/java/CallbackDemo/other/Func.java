package CallbackDemo.other;

/**
 * @author huishen
 * @date 2019-06-19 11:38
 */
public class Func {

    public void func(String message, Callback callback) {
        new Thread(() -> {
            try {
                System.out.println("func start");
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callback.onSucceed("Func func succeed, message: " + message);
        }).start();
    }

    public interface Callback {
        void onSucceed(String data);
    }

}
