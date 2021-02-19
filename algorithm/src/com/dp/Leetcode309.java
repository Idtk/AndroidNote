package com.dp;

public class Leetcode309 {
    public static void exec(){
        int[] prices = new int[]{1,2,3,0,2};
        int result = maxProfit(prices);
        System.out.println("result: "+result);
    }

    public static int maxProfit(int[] prices) {
        if(prices.length == 0){
            return 0;
        }

        int[] dp = new int[3]; // 最大收益
        // dp[0] 持股
        // dp[1] 不持股，在冷静期
        // dp[2] 不持股，不在冷静期

        dp[0] = -prices[0];
        dp[1] = 0;
        dp[2] = 0;

        for(int i=1; i<prices.length; i++){
            int f0 = Math.max(dp[0], dp[2]-prices[i]); 
            int f1 = dp[0]+prices[i];
            int f2 = Math.max(dp[1], dp[2]);
            dp[0] = f0;
            dp[1] = f1;
            dp[2] = f2;
        }

        return Math.max(dp[1], dp[2]);
    }
}
