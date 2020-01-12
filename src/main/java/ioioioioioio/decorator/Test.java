package ioioioioioio.decorator;

/**
 * @author huishen
 * @date 2019-08-20 22:23
 */
public class Test {

    public static void main(String[] args) {
        ABatterCake aBatterCake = new BatterCake();
        aBatterCake = new SausageDecorator(aBatterCake);
        aBatterCake = new SausageDecorator(aBatterCake);
        aBatterCake = new EggDecorator(aBatterCake);
        String desc = aBatterCake.desc();
        int cost = aBatterCake.cost();
    }
}
