package demo;

/**
 * @author huishen
 * @date 2019-04-30 17:21
 */
public class TmpTest {

    public static void main(String[] args) {
        TmpTest test = new TmpTest();
        int[] array = {};
        int maxProfit = test.maxProfit(array);
        System.out.println(maxProfit);
    }

    public int maxProfit(int[] prices) {
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxprofit += prices[i] - prices[i - 1];
            }
        }
        return maxprofit;
    }

}
