package com.top100;

public class LT100_42 {

    public static void exec(){
        int res = trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1});
        System.out.println("res: "+res);
    }

    public static int trap(int[] height) {
        int left = 1;
        int right = height.length-2;
        int max_left=0;
        int max_right=0;
        int ans = 0;
        for(int i=0; i<height.length-1; i++){
            if(height[left-1]<height[right+1]){
                max_left = Math.max(height[left-1], max_left);
                if(max_left>height[left]){
                    ans += (max_left-height[left]);
                }
                left++;
            }else{
                max_right = Math.max(height[right+1], max_right);
                if(max_right>height[right]){
                    ans += (max_right-height[right]);
                }
                right--;
            }
        }

        return ans;
    }
}
