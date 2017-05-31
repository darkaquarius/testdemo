package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_08 {

    public int[] expand1(int[] arr){
        int[] b = new int[arr.length * 2];
        System.arraycopy(arr, 0, b, 0, arr.length);
        return b;
    }

    public int[] expand2(int[] arr){
        int b[] = new int[arr.length * 2];
        for(int i = 0; i < arr.length; i++){
            b[i] = arr[i];
        }
        return b;
    }

    public static void main(String[] args){
        int[] a = {1, 2, 3};
        Practice04_08 p = new Practice04_08();
        // int[] res = p.expand1(a);
        int[] res = p.expand2(a);
        for(int i = 0; i < res.length; i++){
            System.out.print(res[i] + "\t");
        }
    }
}
