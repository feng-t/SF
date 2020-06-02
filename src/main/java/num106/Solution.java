package num106;

import tree.TreeNode;

public class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (null == inorder || inorder.length==0 || null == postorder || postorder.length==0) {
            return null;
        }
        return build(inorder.length, inorder,0,postorder,postorder.length-1);
    }

    private TreeNode build(int length, int[] inorder, int inS, int[] postorder, int postE) {
        int value = postorder[postE];
        int inIndex = inS;
        while (inorder[inIndex] != value && inIndex < inS + length) {
            inIndex++;
        }
        int leftLength = inIndex-inS;
        int rightLength= length-leftLength-1;
        TreeNode node = new TreeNode(value);
        if (leftLength>0) {
            node.left = build(leftLength, inorder, inS, postorder, postE-1 -rightLength);
        }
        if (rightLength>0) {
            node.right = build(rightLength, inorder, inIndex+1, postorder, postE -1);
        }
        return node;
    }
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        return buildTree(inorder, inorder.length - 1, 0, postorder, postorder.length - 1);
    }

    private TreeNode buildTree(int[] inorder, int end, int start, int[] postorder, int idx) {
        if (idx < 0 || start > end) return null;
        int i;
        for (i = end; i >= start; i--) {
            if (inorder[i] == postorder[idx]) break;
        }
        TreeNode root = new TreeNode(postorder[idx]);
        root.left = buildTree(inorder, i - 1, start, postorder, idx - end + i - 1);
        root.right = buildTree(inorder, end, i + 1, postorder, idx - 1);
        return root;
    }
}
