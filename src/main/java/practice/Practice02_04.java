package practice;

/**
 * Created by huishen on 16/12/24.
 */
public class Practice02_04 {

    public int func1(int n){
        int result = 1;
        for(int i = n; i > 0; i--){
            result = result * i;
        }
        return result;
    }



    public static void main(String args[]){
        Practice02_04 practice2_04 = new Practice02_04();
        int n = 10;
        int result1 = practice2_04.func1(10);
        System.out.println(result1);
    }

}
