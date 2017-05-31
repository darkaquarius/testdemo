package practice;

/**
 * Created by huishen on 16/12/25.
 */
public class Practice02_09 {

    public void func(int n){
        //2n-1
        //一行
        for(int i = 1; i < n; i++){
            //前面的空格
            for(int j = 1; j < n - i; j++){
                System.out.print(" ");
            }
            for(int k = 1; k < 2 * i; k++){
                System.out.print("*");
            }
            //后面的空格
            for(int j = 1; j < n - i; j++){
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        new Practice02_09().func(5);
    }
}
