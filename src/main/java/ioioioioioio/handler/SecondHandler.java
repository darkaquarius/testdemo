package ioioioioioio.handler;

/**
 * @author huishen
 * @date 2019-08-20 22:49
 */
public class SecondHandler extends Handler {

    @Override
    void exec(String str) {
        System.out.println("SecondHandler exec: " + str);
        if (nextHandler != null) {
            nextHandler.exec(str);
        }
    }
}
