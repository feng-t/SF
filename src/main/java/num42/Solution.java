package num42;

public class Solution {
    public static void main(String[] args) {
        Solution so = new Solution();
        so.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4});
    }

    public int maxSubArray(int[] nums) {
        if (nums.length==1){
            return nums[0];
        }
        int max=Integer.MIN_VALUE;
        int temp=0;
        for (int num : nums) {
            temp+=num;
            if (temp<num){
                temp = num;
            }
            max=Math.max(max,temp);
        }
        return max;
    }
}
