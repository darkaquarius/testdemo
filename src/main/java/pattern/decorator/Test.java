package pattern.decorator;

public class Test {

    public static void main(String[] args) {
        BatterCake batterCake = new BatterCake();
        EggDecorator eggDecorator = new EggDecorator(batterCake);
        EggDecorator eggDecorator1 = new EggDecorator(eggDecorator);
        SausageDecorator sausageDecorator = new SausageDecorator(eggDecorator1);
        System.out.println(sausageDecorator.getDesc());
        System.out.println(sausageDecorator.price());
    }

}
