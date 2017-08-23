package extendsdemo;

/**
 * Created by huishen on 17/7/28.
 *
 */

public class Person {
    protected String name = "person";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println(this.name);
    }
}

