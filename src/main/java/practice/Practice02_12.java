package practice;

/**
 * Created by huishen on 16/12/25.
 */
public class Practice02_12 {

    public int func(int n){
        int i = 10;
        int z = 0;
        int sum = 0;
        do{
            z = n % 10;
            sum += z;
            n /= 10;
        }while(n > 0);

        return sum;
    }

    public static void main(String args[]){
        int res = new Practice02_12().func(12345);
        System.out.println(res);
    }
}
