package practice;

/**
 * Created by huishen on 17/1/6.
 */
public class Practice03_10 {

    public void func(int n){
        System.out.println(n);
        while(n != 1){
            if(0 == n % 2){
                n /= 2;
                System.out.println(n);
            }else{
                n  = 3 * n + 1;
                System.out.println(n);
            }
        }


    }

    public static void main(String args[]){
        new Practice03_10().func(10);
    }
}
