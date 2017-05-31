package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_05 {

    public void minMax(int[] arr){
        int min = arr[0];
        int max = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }else if(arr[i] < min){
                min = arr[i];
            }
        }
        System.out.println("max:" + max);
        System.out.println("min:" + min);
    }

    public static void main(String args[]){
        Practice04_05 p = new Practice04_05();
        p.minMax(new int[]{1,2,3,6,4,5});
    }
}
