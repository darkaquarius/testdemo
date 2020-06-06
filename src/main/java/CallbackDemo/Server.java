package CallbackDemo;

/**
 * @author huishen
 * @date 2019-06-19 09:43
 */
public class Server implements Runnable {

    private String message;

    private MyCallback callback;

    public Server(String message, MyCallback callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread:" + Thread.currentThread() + "server run, message: " + message);
            Thread.sleep(2_000);
            callback.onCompletion("succeed, message: " + message, null);
        } catch (Exception e) {
            callback.onCompletion(null, e);
        }

    }
}
