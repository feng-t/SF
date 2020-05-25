package num100;

import tree.TreeNode;

public class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p==null&&q==null){
            return true;
        }else if (p==null||q==null){
            return false;
        }else {
            if (p.val==q.val){
                boolean b = isSameTree(p.left, q.left);
                boolean b1 = isSameTree(p.right, q.right);
                return b&&b1;
            }else {
                return false;
            }
        }
    }
}
