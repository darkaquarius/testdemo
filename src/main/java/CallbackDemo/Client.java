package CallbackDemo;

/**
 * @author huishen
 * @date 2019-06-19 09:43
 */
public class Client {

    public void send(String message, MyCallback callback) {
        Server server = new Server(message, callback);
        new Thread(server).start();
    }

}
