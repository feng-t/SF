package tree;

import java.util.Deque;
import java.util.LinkedList;

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
    public static TreeNode getTreeNodeOfArray(Integer[] arr){
        if (arr.length==0){
            return null;
        }
        TreeNode head = new TreeNode(arr[0]);
        Deque<TreeNode> list = new LinkedList<>();
        list.offer(head);
        for (int i = 1; i < arr.length; i++) {
            TreeNode poll = list.poll();
            if (arr[i]!=null&&poll!=null) {
                if ((i & 1) == 1) {
                    //left
                    list.offerFirst(poll);
                    list.offer(poll.setLeft(arr[i]));
                } else {
                    //right
                    list.offer(poll.setRight(arr[i]));

                }
            }else {
                list.offerFirst(poll);
            }
        }
        return head;
    }
}
