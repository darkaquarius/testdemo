package ioioioioioio.handler.copy;

/**
 * @author huishen
 * @date 2019-08-21 09:46
 */
public class FirstHandler extends Handler {

    @Override
    public void exec(String data) {
        System.out.println("FirstHandler exec, data: " + data);
        if (nextHandler != null) {
            nextHandler.exec(data);
        }
    }
}
