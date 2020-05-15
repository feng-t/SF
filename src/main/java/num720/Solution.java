package num720;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.longestWord(new String[]{"b","br","bre","brea","break","breakf","breakfa","breakfas","breakfast","l","lu","lun","lunc","lunch","d","di","din","dinn","dinne","dinner"});
    }
    String rtword = "";
    private TreeNode tree = new TreeNode();
    public String longestWord(String[] words) {
        for(String word : words) {
            insert(word);
        }
        find(tree);
        return rtword;
    }

    private void insert(String word) {
        TreeNode current = tree;
        for(int i=0;i<word.length();i++) {
            int index = word.charAt(i)-'a';
            if(current.children[index]==null) {
                current.children[index] = new TreeNode();
            }
            current = current.children[index];
        }
        current.isWord = true;
        current.word = word;
    }

    private void find(TreeNode current) {
        if(current.word.length()>rtword.length()) {
            rtword = current.word;
        }
        for(int i=0;i<current.children.length;i++) {
            if(current.children[i]!=null&&current.children[i].isWord) {
                find(current.children[i]);
            }
        }
    }

    class TreeNode {
        TreeNode[] children = new TreeNode[26];
        boolean isWord = false;
        String word = "";
    }
}
