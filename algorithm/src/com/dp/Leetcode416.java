package com.dp;

public class Leetcode416 {
    public static void exec(){
        int[] nums = new int[]{1,5,11,5};
        boolean result = canPartition(nums);
        System.out.println("result: "+result);
    }

    public static boolean canPartition(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for(int num:nums){
            sum += num;
        }

        // 奇数判断
        if((sum & 1) == 1){
            return false;
        }

        int target = sum/2;
        boolean[] dp = new boolean[target+1];
        dp[0] = true;
        if(nums[0] <= target){
            dp[nums[0]] = true;
        }

        for(int i=1; i<len; i++){
            for(int j=target; j>= nums[i]; j--){
                if(dp[target]){
                    return true;
                }
                dp[j] = dp[j] || dp[j-nums[i]];
            }
        }
        return dp[target];
    }
}
