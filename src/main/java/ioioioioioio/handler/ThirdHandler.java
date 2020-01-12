package ioioioioioio.handler;

/**
 * @author huishen
 * @date 2019-08-20 22:54
 */
public class ThirdHandler extends Handler {

    @Override
    void exec(String str) {
        System.out.println("ThirdHandler exec: " + str);
        if (nextHandler != null) {
            nextHandler.exec(str);
        }
    }
}
