package tree;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huishen
 * @date 2019-05-09 17:10
 */
@Data
@NoArgsConstructor
public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }
}
