package practice;

/**
 * Created by huishen on 17/1/6.
 */
public class Practice03_11 {

    public void func(){
        for(int i = 100; i <= 999; i++){
            for(int j = 100; j <= 999; j++){
                int a = i / 100;
                int b = i / 10 % 10;
                int c = i % 10;
                int x = j / 100;
                int y = j / 10 % 10;
                int z = j % 10;

                if(isBalance(i) && isBalance(j) && isBalance(10 * a + x)
                    && isBalance(10 * b + y)
                    && isBalance(10 * c + z)){
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println("------------------");
                }
            }
        }
    }

    private boolean isBalance(int n){
        int sqrt = (int) Math.sqrt(n);
        return n == sqrt * sqrt;
    }

    public static void main(String args[]){
        new Practice03_11().func();
    }
}
