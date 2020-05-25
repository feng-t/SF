package num106;

import tree.TreeNode;

public class Solution {
    /**
     * 中序遍历 inorder = [9,3,15,20,7]
     * 后序遍历 postorder = [9,15,7,20,3]
     * [3,9,20,null,null,15,7]
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        return preOrder(inorder,postorder,postorder.length-1,0,inorder.length-1);
    }
    private TreeNode preOrder(int[] inorder, int[] postorder, int postEnd, int inStart, int inEnd){
        TreeNode node = new TreeNode(postorder[postEnd]);
        int in =0 ;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i]==postorder[postEnd]){
                in = i;
                break;
            }
        }

        return node;
    }

    /**
     * 中序遍历
     * @param node
     */
    public void inorder(TreeNode node){
        if (node!=null){
            inorder(node.left);
            System.out.println(node.val);
            inorder(node.right);
        }
    }

    /**
     * 后序遍历
     * @param node
     */
    public void postorder(TreeNode node){
        if (node!=null){
            postorder(node.left);
            postorder(node.right);
            System.out.println(node.val);
        }
    }
}
