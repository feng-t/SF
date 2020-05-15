package num113;

import tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    public static void main(String[] args) {
        new Solution().start();
    }
    public void start(){
        TreeNode root = new TreeNode(1);
        TreeNode left = root.setLeft(5);
        left.setLeft(4);
        left.setRight(2);
        TreeNode right = root.setRight(3);
        right.setLeft(6);
        right.setRight(7);
        Set<List<Integer>> lists = new LinkedHashSet<List<Integer>>();
        path(root, 10, lists);
        System.out.println("");
    }


    private List<Integer> path(TreeNode root, int v,Set<List<Integer>> arr) {
        if (root != null&&v>0) {
            List<Integer> path = path(root.left, v - root.val, arr);
            if (path!=null){
                path.add(root.val);
            }
            List<Integer> integers = path(root.right, v - root.val, arr);
            if (integers!=null){
                integers.add(root.val);
            }
        }
        return v==0?new ArrayList<Integer>():null;
    }
}
