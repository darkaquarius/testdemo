package practice;

/**
 * Created by huishen on 16/12/25.
 */
public class Practice02_13 {

    public boolean func(int n){
        for(int i = 2; i < Math.sqrt(n); i++){
            if(0 == (n % i)){
                return false;
            }else{
                continue;
            }
        }
        return true;
    }

    public static void main(String args[]){
        System.out.println(new Practice02_13().func(17));
    }
}
