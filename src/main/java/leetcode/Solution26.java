package leetcode;

public class Solution26 {
    public int removeDuplicates(int[] nums) {
        if (0 == nums.length) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return i + 1;

    }

    public static void main(String[] args) {
        int[] nums = {0, 1};
        int length = new Solution26().removeDuplicates(nums);
    }
}
