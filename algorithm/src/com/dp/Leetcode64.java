package com.dp;

import java.util.Arrays;

public class Leetcode64 {
    
    public static void exec(){
        int[][] grid = new int[][]{
            new int[]{1,3,1},
            new int[]{1,5,1},
            new int[]{4,2,1}
        };

        int result = minPathSum(grid);
        System.out.println("result: "+result);
    }

    public static int minPathSum(int[][] grid) {
        if(grid.length ==0 || grid[0].length == 0){
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i=0; i<m; i++){
            dp[0] = dp[0]+grid[i][0];
            for(int j=1; j<n; j++){
                dp[j] = Math.min(dp[j-1], dp[j])+grid[i][j];
            }
        }

        return dp[n-1];
    }
}
