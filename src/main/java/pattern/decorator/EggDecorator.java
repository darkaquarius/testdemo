package pattern.decorator;

public class EggDecorator extends ABatterCakeDecorator {

    public EggDecorator(ABatterCake aBatterCake) {
        super(aBatterCake);
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " 加一个鸡蛋 ";
    }

    @Override
    protected int price() {
        return super.price() + 1;
    }
}
