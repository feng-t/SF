package num110;

import tree.TreeNode;

public class Solution {
    public boolean isBalanced(TreeNode root) {
        int height = getHeight(root);
        System.out.println(height);
        return height<0;
    }
    private int getHeight(TreeNode node){
        if (node==null){
            return 0;
        }
        int left = getHeight(node.left);
        if (left==-1){
            return -1;
        }
        int right = getHeight(node.right);
        if (right==-1){
            return -1;
        }
        if (left-right>1||right-left>1){
            return -1;
        }
        return Math.max(left,right)+1;
    }
}
