package singletondemo;

public abstract class Father extends GrandFather {
    public Father() {
        System.out.println("this is Father");
    }

    public void play() {
        eat();
    }

}
