package tree;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void main(String[] args) {
        // List<Integer> list = Arrays.asList(1, 2, 2, 3, null, null, 3, 4, null, null, 4);
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        TreeNode root = create(list);
        // bfsRecursion(root);
        // List<TreeNode> treeNodes = bfsIteration(root);
        // dfsRecursion(root);
        // dfsIterationPreOrder(root);
        List<TreeNode> treeNodes = dfsIterationInOrder(root);
        // List<TreeNode> treeNodes = dfsIterationPostOrder(root);
        System.out.println();
    }

    /**
     * BFS的递归形式
     * 完全二叉树
     * <p>
     * 结果返回的是 List<List<TreeNode>> 类型
     * 每当DFS遍历到一个新的节点，就把它加入到所在层的list里面去
     */
    public static List<List<TreeNode>> bfsRecursion(TreeNode root) {
        if (null == root) {
            return null;
        }
        List<List<TreeNode>> list = new ArrayList<>();
        doBfs(root, 0, list);
        return list;
    }

    private static void doBfs(TreeNode root, int level, List<List<TreeNode>> list) {
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
        doBfs(root.getLeft(), level + 1, list);
        doBfs(root.getRight(), level + 1, list);
    }

    /**
     * BFS的非递归形式
     * <p>
     * 用了Queue的数据结构
     */
    public static List<TreeNode> bfsIteration(TreeNode root) {
        List<TreeNode> ret = new ArrayList<>();
        if (null == root) {
            return ret;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // 处理
            ret.add(node);
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return ret;
    }

    /**
     * BFS的非递归形式
     * <p>
     * 用 curNum 和 nextNum 来维护当前层的node数量和下一层的node数量
     * curNum 和 nextNum 暂时没有发现有什么用
     */
    public static List<TreeNode> bfsIteration1(TreeNode root) {
        if (null == root) {
            return null;
        }

        List<TreeNode> ret = new ArrayList<>();
        int curNum = 1;
        int nextNum = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            ret.add(node);
            curNum--;
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
            }
        }
        return ret;
    }

    /**
     * BFS的非递归形式
     * 完全二叉树
     *
     * 用了Queue的数据结构
     * 用 curNum 和 nextNum 来维护当前层的node数量和下一层的node数量
     *
     */
    // public static List<List<Integer>> bfsIteration(TreeNode root) {
    //     ArrayList<List<Integer>> list = new ArrayList<>(Collections.emptyList());
    //     if (null == root) {
    //         return list;
    //     }
    //     int curNum = 1;
    //     int nextNum = 0;
    //     Queue<TreeNode> queue = new LinkedList<>();
    //     queue.add(root);
    //     ArrayList<Integer> tmpList = new ArrayList<>();
    //     while (!queue.isEmpty()) {
    //         TreeNode node = queue.poll();
    //         curNum--;
    //         tmpList.add(node.getValue());
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
    //             list.add(tmpList);
    //             tmpList = new ArrayList<>();
    //         }
    //     }
    //     return list;
    // }

    /**
     * DFS分为：preOrder(前序), inOrder(中序), postOrder(后序)
     * DFS的递归形式
     * <p>
     * 以preOrder为例
     */

    public static List<TreeNode> dfsRecursion(TreeNode root) {
        List<TreeNode> ret = new ArrayList<>();
        if (root != null) {
            ret.add(root);
            dfsRecursion(root.getLeft());
            dfsRecursion(root.getRight());
        }
        return ret;
    }

    /**
     * DFS的迭代形式，前序
     * <p>
     * <p>
     * 根 -> 左 -> 右
     * 用Stack，先push右子节点，再push左子节点
     * 那么就是左子节点先出来，右子节点再出来
     */
    public static List<TreeNode> dfsIterationPreOrder(TreeNode root) {
        List<TreeNode> ret = new ArrayList<>();
        if (null == root) {
            return ret;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 在这里做处理，也可以是其他的处理流程
            ret.add(node);
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
        return ret;
    }

    /**
     * DFS的迭代形式，中序
     */
    public static List<TreeNode> dfsIterationInOrder(TreeNode root) {
        ArrayList<TreeNode> ret = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.getLeft();
            }
            // 最左边的节点
            cur = stack.pop();
            ret.add(cur);
            cur = cur.getRight();
        }
        return ret;
    }

    /**
     * DFS的迭代形式，后序
     * <p>
     * 根 -> 右 -> 左  =>  左 -> 右 -> 根
     * 反转通过Deque来实现(LinkedList实现了Deque)
     */
    public static List<TreeNode> dfsIterationPostOrder(TreeNode root) {
        LinkedList<TreeNode> ret = new LinkedList<>();

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 处理
            ret.addFirst(node);
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
        }

        return ret;
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
    public static TreeNode create(List<Integer> list) {
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


    // public static void main(String[] args) {
    //     BinaryTreeTest test = new BinaryTreeTest();
    //     List<Integer> list = Arrays.asList(1, 2);
    //     TreeNode root = test.create(list);
    //     int i = test.minDepth(root);
    //     System.out.println(i);
    // }
    //
    // private static int tmp = 0;

    // public int minDepth(TreeNode root) {
    //     if (null == root) {
    //         return 0;
    //     }
    //
    //     inter(root, 0);
    //     return tmp;
    // }
    //
    // public void inter(TreeNode root, int depth) {
    //     if (null == root) {
    //         return;
    //     }
    //     // 叶子节点
    //     if (null == root.getLeft() && null == root.getRight()) {
    //         depth = depth + 1;
    //         if (0 == tmp || depth < tmp) {
    //             tmp = depth;
    //         }
    //     } else {
    //         inter(root.getLeft(), depth + 1);
    //         inter(root.getRight(), depth + 1);
    //     }
    // }

}
