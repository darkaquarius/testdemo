package practice;

/**
 * Created by huishen on 17/1/6.
 */
public class Practice03_07 {

    public double func(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
    }

    public static void main(String args[]){
        System.out.println(Math.pow(2, 2));
        double res = new Practice03_07().func(2 ,1 , 1, 2);
        System.out.println(res);
    }
}
