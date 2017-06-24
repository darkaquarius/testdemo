package demo;

/**
 * Created by huishen on 17/6/14.
 *
 */
public class TriangleDemo {

   public void func(int rows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0 ; j < rows - 1 - i; j++) {
                System.out.print(" ");
            }
            for (int j = i + 1; j > 0; j--) {
                System.out.print(j);
            }
            for (int j = 2; j <= i + 1; j++) {
                System.out.print(j);
            }
            // for (int j = 0 ; j < rows - 1 - i; j++) {
            //     System.out.print(" ");
            // }
            System.out.println("");
        }
   }

   public static void main(String[] args) {
       int rows = 5;
       new TriangleDemo().func(rows);

   }

}
