package com.greedy;

public class Leetcode53 {
    public static void exec(){
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int result = maxSubArray(nums);
        System.out.println("result: "+result);
    }

    public static int maxSubArray(int[] nums) {
        int sum = 0;
        int ans = nums[0];
        for(int i=1; i<nums.length ;i++){
            if(sum>0){
                sum +=nums[i];
            }else{
                sum = nums[i];
            }

            ans = Math.max(sum, ans);
        }
        return ans;
    }
}
