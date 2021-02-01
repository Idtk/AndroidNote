package com.dfs;

public class Leetcode547 {
    public static void exec(){
        // int[][] isConnected = new int[][]{
        //     new int[]{1,1,0},
        //     new int[]{1,1,0},
        //     new int[]{0,0,1}
        // };
        int[][] isConnected = new int[][]{
            new int[]{1,0,0,1},
            new int[]{0,1,1,0},
            new int[]{0,1,1,1},
            new int[]{1,0,1,1}
        };
        int result = findCircleNum(isConnected);
        System.out.println("result: "+result);
    }

    public static int findCircleNum(int[][] isConnected) {
        if(isConnected.length == 0 || isConnected[0].length == 0){
            return 0;
        }
        int provinces = isConnected.length;
        boolean[] visited = new boolean[provinces];
        int nums = 0;
        for(int i = 0; i<provinces; i++){// 行
            if(!visited[i]){
                nums++;
                dfs(isConnected,visited,provinces,i);
            }
        }
        return nums;
    }

    public static void dfs(int[][] isConnected, boolean[] visited , int provinces ,int i ){
        for(int j=0 ; j<provinces; j++){// 小正方形
            if(isConnected[i][j] == 1 && !visited[j]){
                visited[j] = true;
                dfs(isConnected, visited, provinces, j);
            }
        }
    }
}
