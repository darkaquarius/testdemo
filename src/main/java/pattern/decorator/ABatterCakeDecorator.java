package pattern.decorator;

public class ABatterCakeDecorator extends ABatterCake {
    private ABatterCake aBatterCake;

    public ABatterCakeDecorator(ABatterCake aBatterCake) {
        this.aBatterCake = aBatterCake;
    }

    @Override
    public String getDesc() {
        return aBatterCake.getDesc();
    }

    @Override
    protected int price() {
        return aBatterCake.price();
    }
}
