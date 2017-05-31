package practice;

/**
 * Created by huishen on 17/1/6.
 */
public class Practice03_05 {
    public int func(int n){
        int m = 10;
        int count = 0;

        while(n > 0){
            n = n / m;
            count++;
        }

        return count;
    }

    public static void main(String[] args){
        Practice03_05 practice03_05 = new Practice03_05();
        System.out.println(practice03_05.func(11));
    }
}
