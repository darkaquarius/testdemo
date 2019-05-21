package demo;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * @author huishen
 * @date 2019-05-08 12:00
 */
public class DecimalFormatDemo {

    /**
     * 0
     */
    @Test
    public void test() {
        int num1 = 4;
        int num2 = 35;
        DecimalFormat df = new DecimalFormat("######0.00");
        String ret = df.format( (double) num1 / num2);
    }

}
