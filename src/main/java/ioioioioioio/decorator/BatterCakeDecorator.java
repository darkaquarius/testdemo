package ioioioioioio.decorator;

/**
 * @author huishen
 * @date 2019-08-20 22:20
 */
public abstract class BatterCakeDecorator extends ABatterCake {
    private ABatterCake aBatterCake;

    public BatterCakeDecorator(ABatterCake aBatterCake) {
        this.aBatterCake = aBatterCake;
    }

    @Override
    protected String desc() {
        return aBatterCake.desc();
    }

    @Override
    protected int cost() {
        return aBatterCake.cost();
    }
}
