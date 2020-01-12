package ioioioioioio.handler;

/**
 * @author huishen
 * @date 2019-08-20 22:50
 */
public class Test {

    public static void main(String[] args) {
        Handler firstHandler = new FirstHandler();
        SecondHandler secondHandler = new SecondHandler();
        ThirdHandler thirdHandler = new ThirdHandler();

        firstHandler.setNextHandler(secondHandler);
        secondHandler.setNextHandler(thirdHandler);

        firstHandler.exec("hello");
    }

}
