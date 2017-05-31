package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_10 {

    public int[] bubbleSort(int arr[]){
        int temp;
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr.length - i - 1; j++){
                if(arr[j] > arr[j+1]) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    public static void main(String args[]){
        int[] res = new Practice04_10().bubbleSort(new int[]{1, 3, 2, 7, 5});

    }

}
