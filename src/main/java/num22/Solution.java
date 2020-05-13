package num22;

import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.generateParenthesis(5);
    }
    public List<String> generateParenthesis(int n) {
        for (int i = 0; i < n; i++) {

        }
        return null;
    }
    public String getBrackets(int n,String str){
        if (n == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append("(");
        }
        if (str!=null&&!"".equals(str)){
            builder.append(str);
        }
        for (int i = 0; i < n; i++) {
            builder.append(")");
        }
        return builder.toString();
    }
}
