package tree;

import java.util.HashMap;

/**
 * @author huishen
 * @date 2019-05-09 17:09
 */
public class BinaryTreeTest2 {

    /**
     * 简易的建立二叉树
     */
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        root.setRight(node2);
        node2.setRight(node3);

        BinaryTreeTest2 instance = new BinaryTreeTest2();
        boolean balanced = instance.isBalanced(root);
        System.out.println(balanced);
    }


    private HashMap<TreeNode, Integer> map = new HashMap<>();

    public boolean isBalanced(TreeNode root) {
        if (null == root) {
            return true;
        }

        return inter(root);
    }

    public boolean inter(TreeNode root) {
        boolean leftRet = inter(root.getLeft());
        boolean rightRet = inter(root.getRight());

        if (!leftRet || !rightRet) {
            return false;
        }

        int leftDepth = maxDepth(root.getLeft());
        int rightDepth = maxDepth(root.getRight());
        if (Math.abs(leftDepth - rightDepth) > 1) {
            return false;
        }

        return true;
    }

    public int maxDepth(TreeNode root) {

        Integer max = map.get(root);
        if (max != null) {
            return max;
        }

        if (null == root) {
            map.put(root, 0);
            return 0;
        }
        max = Math.max(maxDepth(root.getLeft()), maxDepth(root.getRight())) + 1;
        map.put(root, max);
        return max;
    }


}
