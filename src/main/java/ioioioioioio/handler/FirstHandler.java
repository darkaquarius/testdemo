package ioioioioioio.handler;

/**
 * @author huishen
 * @date 2019-08-20 22:47
 */
public class FirstHandler extends Handler {

    @Override
    void exec(String str) {
        System.out.println("FirstHandler exec: " + str);
        if (nextHandler != null) {
            nextHandler.exec(str);
        }
    }
}
