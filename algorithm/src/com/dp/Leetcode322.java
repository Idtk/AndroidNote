package com.dp;

import java.util.Arrays;

public class Leetcode322 {
    public static void exec(){
        int[] coins = new int[]{2};
        int result = coinChange(coins, 3);
        System.out.println("result: "+result);
    }

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp, amount+1);
        dp[0]=0;
        for(int coin : coins){
            for(int j=coin; j<=amount; j++){
                dp[j] = Math.min(dp[j], dp[j-coin]+1);
            }
        }
        // 如果需要的值仍为初始值，则返回-1
        if(dp[amount] == amount+1){
            dp[amount] = -1;
        }

        return dp[amount];
    }
}
