package ioioioioioio.handler.copy;

/**
 * @author huishen
 * @date 2019-08-21 09:48
 */
public class SecondHandler extends Handler {

    @Override
    public void exec(String data) {
        System.out.println("SecondHandler exec, data: " + data);
        if (nextHandler != null) {
            nextHandler.exec(data);
        }
    }
}
