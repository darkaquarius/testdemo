package ioioioioioio.decorator;

/**
 * @author huishen
 * @date 2019-08-20 22:21
 */
public class EggDecorator extends BatterCakeDecorator {

    public EggDecorator(ABatterCake aBatterCake) {
        super(aBatterCake);
    }

    @Override
    protected String desc() {
        return super.desc() + "加鸡蛋";
    }

    @Override
    protected int cost() {
        return super.cost() + 1;
    }
}
