package com.dfs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Leetcode695 {

    private static int[][] direction = new int[][]{
        new int[]{0,-1},
        new int[]{1,0},
        new int[]{0,1},
        new int[]{-1,0}
    };
    private static int m,n;

    public static void exec(){
        int[][] grid = new int[][]{
            new int[]{0,0,1,0,0,0,0,1,0,0,0,0,0},
            new int[]{0,0,0,0,0,0,0,1,1,1,0,0,0},
            new int[]{0,1,1,0,1,0,0,0,0,0,0,0,0},
            new int[]{0,1,0,0,1,1,0,0,1,0,1,0,0},

            new int[]{0,1,0,0,1,1,0,0,1,1,1,0,0},
            new int[]{0,0,0,0,0,0,0,0,0,0,1,0,0},
            new int[]{0,0,0,0,0,0,0,1,1,1,0,0,0},
            new int[]{0,0,0,0,0,0,0,1,1,0,0,0,0}
        };

        int result = maxAreaOfIsland(grid);
        System.out.println("result: "+result);
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        m = grid.length;
        n = grid[0].length;

        int max = 0;
        for(int i = 0; i<m ; i++){
            for(int j = 0; j<n ; j++){
                int curr = dfs(grid, i, j);
                max = Math.max(max, curr);
            }
        }
        
        return max;
    }

    public static int dfs(int[][] grid,int i , int j){
        if(i<0 || i>=m || j<0 || j>=n || grid[i][j] == 0){
            return 0;
        }
        grid[i][j] = 0;
        int count = 1;
        for(int[] d:direction){
            count += dfs(grid, i+d[0],j+d[1]);
        }

        return count;

    }
}
