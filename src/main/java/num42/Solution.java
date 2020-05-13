package num42;

public class Solution {
    public static void main(String[] args) {
        Solution so = new Solution();
        so.trap(new int[]{4,2,3});
    }

    public int trap(int[] height) {
        if (height.length < 3) {
            return 0;
        }
        int p1 = 0, p2 = height.length-1;
        int h=0;
        while (p1<p2) {
            int t = p1+1;
            int temp=0;
            while (p1!=t&&t<=p2){
                if (height[p1] > height[t]) {
                    temp += Math.max(0, height[p1] - height[t]);
                    t++;
                } else {
                    p1 = t;
                    h+=temp;
                }
            }
            temp=0;
            t = p2 - 1;
            while (p2!=t&&t>=p1){
                if (height[p2] > height[t]) {
                    temp += Math.max(0, height[p2] - height[t]);
                    t--;
                } else {
                    p2 = t;
                    h+=temp;
                }
            }
        }
        return h;
    }
}
