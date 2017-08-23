package extendsdemo;

/**
 * Created by huishen on 17/7/28.
 *
 */

public class Dad extends Person {
    private String name = "Dad";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void printName() {
        System.out.println(super.name);
        System.out.println(this.name);
    }

}
