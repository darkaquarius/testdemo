package ioioioioioio.handler.copy;

/**
 * @author huishen
 * @date 2019-08-21 09:44
 */
public class Test {

    public static void main(String[] args) {
        FirstHandler firstHandler = new FirstHandler();
        SecondHandler secondHandler = new SecondHandler();
        ThirdHandler thirdHandler = new ThirdHandler();

        firstHandler.setNextHandler(secondHandler);
        secondHandler.setNextHandler(thirdHandler);

        firstHandler.exec("this is data");
    }

}
