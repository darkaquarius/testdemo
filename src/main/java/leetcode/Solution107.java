package leetcode;

import tree.TreeNode;

import java.util.*;

public class Solution107 {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (null == root) {
            return Collections.emptyList();
        }
        LinkedList<List<Integer>> ret = new LinkedList<>();
        doLevelOrderBottom(root, 0, ret);

        return ret;
    }

    private void doLevelOrderBottom(TreeNode root, int level, LinkedList<List<Integer>> ret) {
        if (null == root) {
            return;
        }

        if (level >= ret.size()) {
            ArrayList<Integer> subList = new ArrayList<>();
            subList.add(root.val);
//            ret.add(level, subList);
            ret.addFirst(subList);
        } else {
            ArrayList<Integer> subList = (ArrayList<Integer>) ret.get(ret.size() - 1 - level);
            subList.add(root.val);
        }


        doLevelOrderBottom(root.left, level + 1, ret);
        doLevelOrderBottom(root.right, level + 1, ret);
    }
}
