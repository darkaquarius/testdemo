package ioioioioioio.decorator;

/**
 * @author huishen
 * @date 2019-08-20 22:22
 */
public class SausageDecorator extends BatterCakeDecorator {

    public SausageDecorator(ABatterCake aBatterCake) {
        super(aBatterCake);
    }

    @Override
    protected String desc() {
        return super.desc() + "加火腿";
    }

    @Override
    protected int cost() {
        return super.cost() + 2;
    }
}
