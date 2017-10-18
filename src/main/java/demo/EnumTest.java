package demo;

import org.junit.Test;

/**
 * Created by huishen on 16/11/18.
 *
 */

public class EnumTest {

    @Test
    public void test1(){
        int length = User.Age.values().length;
        long l = System.currentTimeMillis() % length;
        User.Age age = null;
        switch ((int) l){
            case 0:
                age = User.Age.values()[0];
                break;
            case 1:
                age = User.Age.values()[1];
                break;
            case 2:
                age = User.Age.values()[2];
                break;
            case 3:
                age = User.Age.values()[3];
                break;
        }
        System.out.println(age.toString());
    }

}
