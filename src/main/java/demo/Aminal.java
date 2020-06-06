package demo;

import org.junit.Test;

public class Aminal {

    public void shout() {
        shout0();
    }

    private void shout0() {
        System.out.println("Aminal shout!");
    }

    @Test
    public void test1() {
        Aminal aminal = new Aminal();
        aminal.shout(); // 这里图省事了
        Tiger tiger = new Tiger();
        tiger.shout();
    }

    @Test
    public void test2() {
        Aminal aminal = new Aminal();
        aminal.shout(); // 这里图省事了
        Aminal tiger = new Tiger();
        tiger.shout();
    }

}

class Tiger extends Aminal {

    @Override
    public void shout() {
        shout0();
    }

    private void shout0() {
        System.out.println("Tiger shout!, NOT override");
    }


}