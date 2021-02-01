package com.dfs;

public class Leetcode200 {
    public static void exec(){
        char[][] grid = new char[][]{
            new char[]{'0','0','0','0','0'},
            new char[]{'0','1','0','0','0'},
            new char[]{'1','0','0','0','0'},
            new char[]{'0','0','0','0','0'}
        };

        int result = numIslands(grid);
        System.out.println("result: "+result);
    }

    private static int m,n;

    private static int[][] direction = new int[][]{
        new int[]{0,-1},
        new int[]{1,0},
        new int[]{0,1},
        new int[]{-1,0}
    };

    public static int numIslands(char[][] grid) {
        if(grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        m = grid.length;
        n =grid[0].length;
        int nums = 0;
        for(int i = 0; i<m; i++){ // 行
            for(int j = 0; j<n ;j++){ // 列
                // 迭代
                if(grid[i][j] != '0'){
                    island(grid, i, j);
                    nums++;
                }
            }
        }

        return nums;
    }

    public static void island(char[][] grid, int i, int j){
        if(i<0 || i>=m || j <0 || j>=n || grid[i][j] == '0'){
            return;
        }
        grid[i][j] = '0';
        for(int[] d:direction){
            island(grid, i+d[0], j+d[1]);
        }
    }
}
