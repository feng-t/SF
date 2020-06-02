package num190;


public class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int i = s.reverseBits(0b11111111111111111111111111111101);
        System.out.println(i);
    }

    public int reverseBits(int n) {
        int a=0;
        for(int i=0;i<=31;i++){
            a=a+((1&(n>>i))<<(31-i));
        }
        return a;
    }

}
