package practice;

/**
 * Created by huishen on 16/12/25.
 */
public class Practice02_14 {

    public void func(){
        for(int i = 1; i<=1000; i++){
            int sum = 0;
            for(int j = 1; j <= i / 2; j++){
                if(0 == i % j){
                    sum += j;
                }
            }

            if(i == sum)
                System.out.print(i + ",");
        }
        System.out.println();
    }

    public static void main(String args[]){
        new Practice02_14().func();
    }
}
