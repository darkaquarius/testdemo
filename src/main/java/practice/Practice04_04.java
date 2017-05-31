package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_04 {
    public int exists(int[] arr, int n){
        for(int i = 0; i< arr.length; i++){
            if(n == arr[i])  return i;
        }
        return -1;
    }

    public static void main(String args[]){

    }
}
