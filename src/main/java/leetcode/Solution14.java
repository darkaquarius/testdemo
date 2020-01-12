package leetcode;

public class Solution14 {
    public String longestCommonPrefix(String[] strs) {

        if (null == strs || 0 == strs.length) {
            return "";
        }

        int minLen = strs[0].length();
        for (String str : strs) {
            if (str.length() < minLen) {
                minLen = str.length();
            }
        }

        StringBuilder prefix = new StringBuilder();
        retry:
        for (int i = 0; i < minLen; i++) {
            char c = '\0';
            try {
                for (String str : strs) {
                    if (c == '\0') {
                        c = str.charAt(i);
                    } else {
                        if (c != str.charAt(i)) {
                            break retry;
                        }
                    }
                }
                prefix.append(c);
            } catch (IndexOutOfBoundsException e) {

            }
        }
        return prefix.toString();
    }

    public static void main(String[] args) {
        String[] array = new String[]{"flower", "flow", "flight"};
        new Solution14().longestCommonPrefix(array);
    }
}
