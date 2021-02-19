package com.dp;

import java.util.Arrays;

public class Leetcode300 {
    public static void exec(){
        int[] nums = new int[]{1,3,6,7,9,4,10,5,6};
        int result = lengthOfLIS(nums);
        System.out.println("result: "+result);
    }

    public static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for(int i=0; i<nums.length; i++){
            for(int j=0; j<i; j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
