package demo;

import org.junit.Test;

/**
 * Created by huishen on 17/2/28.
 */
public class DoubleDemo {

    @Test
    public void test(){
        Double d1 = 0.75;
        Double d2 = 0.35;
        double ret = d1 - d2;
        System.out.println(ret);
    }

    @Test
    public void test2(){

        String str1 = "0";
        String str2 = "0.0";
        String str3 = "0.00";

        String str4 = "0.10";
        String str5 = "0.100";

        double d1 = Double.parseDouble(str1);
        double d2 = Double.parseDouble(str2);
        double d3 = Double.parseDouble(str3);
        double d4 = Double.parseDouble(str4);
        double d5 = Double.parseDouble(str5);

        int compare = Double.compare(d2, 0);
        System.out.println(compare);
        if(0 == compare){
            System.out.println("yes");
        }
    }

    @Test
    public void test3() {
        double d = 1.9;
        int i = (int)d;
        System.out.println(i);
    }

    @Test
    public void test4() {
        int money = 20;
        int ret = money / 3;
        System.out.println(ret);
    }

    @Test
    public void test5() {
        double pre = 1.3;
        double now = 1.1;

        double ret = (now - pre) / pre;
        System.out.println(ret);
    }

    @Test
    public void test6() {
        double ret = Double.parseDouble("0.0000004037");
        System.out.println(ret);
    }

}
