package ioioioioioio.decorator;

/**
 * @author huishen
 * @date 2019-08-20 22:18
 */
public class BatterCake extends ABatterCake {

    @Override
    protected String desc() {
        return "煎饼";
    }

    @Override
    protected int cost() {
        return 8;
    }
}
