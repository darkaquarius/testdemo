package tree;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author huishen
 * @date 2019-05-03 11:13
 * <p>
 * 二叉树的遍历：
 * BFS的递归形式，BFS的非递归形式，DFS的递归形式，DFS的非递归形式
 * <p>
 * https://www.jianshu.com/p/a753d5c733ec
 */

public class BinaryTreeTest {

    // public static void main(String[] args) {
    //     BinaryTreeTest test = new BinaryTreeTest();
    //     List<Integer> list = Arrays.asList(1, 2, 2, 3, null, null, 3, 4, null, null, 4);
    //     TreeNode root = test.create(list);
    //     System.out.println();
    // }

    List<TreeNode> ret = new ArrayList<>();

    /**
     * BFS的递归形式
     */
    public List<List<TreeNode>> traversal(TreeNode root) {
        if (null == root) {
            return null;
        }
        List<List<TreeNode>> list = new ArrayList<>();
        bfs(root, 0, list);
        return list;
    }

    public void bfs(TreeNode root, int level, List<List<TreeNode>> list) {
        if (root == null) {
            return;
        }
        if (level >= list.size()) {
            List<TreeNode> internalList = new ArrayList<>();
            internalList.add(root);
            list.add(internalList);
        } else {
            list.get(level).add(root);
        }
        bfs(root.getLeft(), level + 1, list);
        bfs(root.getRight(), level + 1, list);
    }

    /**
     * BFS的非递归形式
     */
    // public List<TreeNode> bfs(TreeNode root) {
    //     if (null == root) {
    //         return null;
    //     }
    //
    //     List<TreeNode> ret = new ArrayList<>();
    //     int curNum = 1;
    //     int nextNum = 0;
    //     LinkedList<TreeNode> queue = new LinkedList<>();
    //     queue.add(root);
    //     while (!queue.isEmpty()) {
    //         TreeNode node = queue.poll();
    //         ret.add(node);
    //         curNum--;
    //         if (node.getLeft() != null) {
    //             queue.add(node.getLeft());
    //             nextNum++;
    //         }
    //         if (node.getRight() != null) {
    //             queue.add(node.getRight());
    //             nextNum++;
    //         }
    //         if (0 == curNum) {
    //             curNum = nextNum;
    //             nextNum = 0;
    //         }
    //     }
    //     return ret;
    // }
    public List<List<Integer>> bfs(TreeNode root) {
        ArrayList<List<Integer>> list = new ArrayList<>(Collections.emptyList());
        if (null == root) {
            return list;
        }
        int curNum = 1;
        int nextNum = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        ArrayList<Integer> tmpList = new ArrayList<>();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            curNum--;
            tmpList.add(node.getValue());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
                nextNum++;
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
                nextNum++;
            }
            if (0 == curNum) {
                curNum = nextNum;
                nextNum = 0;
                list.add(tmpList);
                tmpList = new ArrayList<>();
            }
        }
        return list;
    }


    /**
     * DFS分为：preOrder(前序), inOrder(中序), postOrder(后序)
     * DFS的递归形式
     * <p>
     * 以preOrder为例
     */
    public void dfs1(TreeNode root) {
        if (root != null) {
            ret.add(root);
            dfs1(root.getLeft());
            dfs1(root.getRight());
        }
    }

    /**
     * DFS的迭代形式
     * <p>
     * 以preOrder为例
     */
    public void dfs2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            ret.add(node);
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
    }

    /**
     * 生成二叉树
     * 入参: [1,2,2,3,3,null,null,4,4]
     * 出参: 二叉树的根节点
     * 1
     * / \
     * 2   2
     * / \
     * 3   3
     * / \
     * 4  4
     */
    public TreeNode create(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        int index = 1;

        TreeNode root = new TreeNode(list.get(0));
        queue.add(root);
        boolean isLeft = true;
        while (index < list.size()) {
            TreeNode parentNode = queue.peek();

            Integer integer = list.get(index);
            TreeNode node = null;
            if (integer != null) {
                node = new TreeNode(integer);
            }
            if (null == parentNode.getLeft() && isLeft) {
                parentNode.setLeft(node);
                isLeft = false;
            } else {
                parentNode.setRight(node);
                queue.poll();
                isLeft = true;
            }
            if (node != null) {
                queue.add(node);
            }
            index++;
        }

        return root;
    }


    public static void main(String[] args) {
        BinaryTreeTest test = new BinaryTreeTest();
        List<Integer> list = Arrays.asList(1, 2);
        TreeNode root = test.create(list);
        int i = test.minDepth(root);
        System.out.println(i);
    }

    private static int tmp = 0;

    public int minDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }

        inter(root, 0);
        return tmp;
    }

    public void inter(TreeNode root, int depth) {
        if (null == root) {
            return;
        }
        // 叶子节点
        if (null == root.getLeft() && null == root.getRight()) {
            depth = depth + 1;
            if (0 == tmp || depth < tmp) {
                tmp = depth;
            }
        } else {
            inter(root.getLeft(), depth + 1);
            inter(root.getRight(), depth + 1);
        }
    }

}
