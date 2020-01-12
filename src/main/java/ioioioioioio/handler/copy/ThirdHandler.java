package ioioioioioio.handler.copy;

/**
 * @author huishen
 * @date 2019-08-21 09:49
 */
public class ThirdHandler extends Handler {

    @Override
    protected void exec(String data) {
        System.out.println("ThirdHandler exec, data: " + data);
        if (nextHandler != null) {
            nextHandler.exec(data);
        }
    }
}
