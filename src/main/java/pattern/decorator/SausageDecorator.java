package pattern.decorator;

public class SausageDecorator extends ABatterCakeDecorator {
    public SausageDecorator(ABatterCake aBatterCake) {
        super(aBatterCake);
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " 加一根香肠 ";
    }

    @Override
    protected int price() {
        return super.price() + 2;
    }
}
