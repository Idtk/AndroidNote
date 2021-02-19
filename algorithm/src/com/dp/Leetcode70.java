package com.dp;

public class Leetcode70 {
    
    public static void exec(){
        int result = climbStairs(5);
        System.out.println("result: "+result);
    }

    private static int[] dp = new int[2];

    public static int climbStairs(int n) {
        dp[0] = 1;
        dp[1] = 1;
        for(int i = 2; i<=n ;i++){
            int sum = dp[0]+dp[1];
            dp[0] = dp[1];
            dp[1] = sum;
        }
        return dp[1];
    }
}
