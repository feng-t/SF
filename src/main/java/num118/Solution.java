package num118;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        List<List<Integer>> generate = s.generate(30);
    }
    public List<List<Integer>> generate(int numRows) {
        if (numRows==0){
            return new ArrayList<>(0);
        }
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> t = new ArrayList<>();
        t.add(1);
        list.add(t);
        for (int i = 2; i <= numRows; i++) {
            List<Integer> arr = new ArrayList<>();
            arr.add(1);
            for (int j = 1; j < i-1; j++) {
                int num = t.get(j - 1) + t.get(j);
                arr.add(num);
            }
            arr.add(1);
            t=arr;
            list.add(arr);
        }
        return list;
    }
}
