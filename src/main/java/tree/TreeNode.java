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
    private Integer value;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }
}
