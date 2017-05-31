package practice;

/**
 * Created by huishen on 17/2/4.
 */
public class Practice04_06 {

    public int[][] zhuanZhi(int[][] arr){
        int row = arr.length;
        int col = arr[0].length;
        int[][] b = new int[col][row];
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                b[j][i] = arr[i][j];
            }
        }

        return b;
    }

    public static void main(String args[]){
        Practice04_06 p = new Practice04_06();
        int[][] res = p.zhuanZhi(new int[][]{{1, 2, 3}, {4, 5, 6}});
        for(int i = 0; i < res.length; i++){
            for(int j = 0; j < res[0].length; j++){
                System.out.print(res[i][j] + "\t");
            }
            System.out.println("\n");
        }

    }

}
