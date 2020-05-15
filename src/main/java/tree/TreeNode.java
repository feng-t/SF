package tree;

public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public int val;
    public TreeNode(int val){
        this.val=val;
    }

    public TreeNode setLeft(int val) {
        this.left=new TreeNode(val);
        return left;
    }

    public TreeNode setRight(int val) {
        this.right = new TreeNode(val);
        return right;
    }
}
