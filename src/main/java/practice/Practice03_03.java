package practice;

/**
 * Created by huishen on 17/1/6.
 */
public class Practice03_03 {

    public int func(int n){
        int sum = 0;
        for(int i = 1; i <= n; i++){
            sum += i;
        }
        return sum;
    }

    public static void main(String args[]){
        Practice03_03 practice03_03 = new Practice03_03();
        int res = practice03_03.func(10);
        System.out.println(res);

    }
}
