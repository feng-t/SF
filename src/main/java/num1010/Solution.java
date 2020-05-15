package num1010;

import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.numPairsDivisibleBy60(new int[]{174,188,377,437,54,498,455,239,183,347,59,199,52,488,147,82});
    }

    private int numPairsDivisibleBy60(int[] time) {
        int cnt =0;
        int[] arr = new int[60];
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = time.length - 1; i >= 0; i--) {
            int temp = time[i] % 60;
            cnt+=arr[(60-temp)%60];
            ++arr[temp];
        }
        return cnt;
    }
}
