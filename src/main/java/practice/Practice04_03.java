package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_03 {

    public double average(int[] a){
        int sum = 0;
        for(int i = 0; i < a.length; i++){
            sum += a[i];
        }
        return (double)sum / a.length;
    }

    public static void main(String args[]){
        Practice04_03 p = new Practice04_03();
        double average = p.average(new int[]{1, 2, 3, 4});
        System.out.println(average);

    }
}
