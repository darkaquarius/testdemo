package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/2/16.
 */
public class MathTest {

    @Test
    public void test1(){
        Double ret = (double) 1 / 10;
        System.out.println(ret);
    }

    @Test
    public void test2() {
        int hashCode = "group_symbol_data_bittrex".hashCode();
        int ret = Math.abs(hashCode) % 50;
        System.out.println("ret: " + ret);
    }

}
