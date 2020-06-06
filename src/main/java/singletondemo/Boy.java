package singletondemo;

public class Boy extends Father {

    public Boy() {
        System.out.println("this is Boy");
    }

    @Override
    public void eat() {
        System.out.println("Boy eat");
    }


}
