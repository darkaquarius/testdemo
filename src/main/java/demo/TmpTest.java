package demo;

/**
 * @author huishen
 * @date 2019-04-30 17:21
 */
public class TmpTest {

    public static void main(String[] args) {
        TmpTest tmpTest = new TmpTest();
        int func = tmpTest.reverse(123);
        System.out.println(func);
    }

    public int reverse(int x) {
        int ret = 0;
        while (x != 0) {
            int s = x % 10;
            ret = ret * 10 + s;
            x = x / 10;
        }
        return x;

    }

}
