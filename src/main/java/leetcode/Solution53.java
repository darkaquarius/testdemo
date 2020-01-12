package leetcode;

public class Solution53 {

    public int maxSubArray(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return 0;
        }

        int pre = 0;
        int ret = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            if (ret < pre) {
                ret = pre;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] array = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int i = new Solution53().maxSubArray(array);
    }
}
