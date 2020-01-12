package pattern.decorator;

public class BatterCake extends ABatterCake {

    @Override
    protected String getDesc() {
        return "煎饼";
    }

    @Override
    protected int price() {
        return 8;
    }
}
