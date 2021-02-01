package com.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode417 {
    public static void exec(){
        int[][] matrix = new int[][]{
            new int[]{1,2,2,3,5},
            new int[]{3,2,3,4,5},
            new int[]{2,4,5,3,1},
            new int[]{6,7,1,4,5},
            new int[]{5,1,1,2,4}
        };

        List<List<Integer>> result = pacificAtlantic(matrix);
        System.out.println("result: "+result.toString());
    }

    static int m,n;

    static int[][] direction = new int[][]{
        new int[]{0,-1},
        new int[]{1,0},
        new int[]{0,1},
        new int[]{-1,0}
    };

    public static List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length == 0){
            return new ArrayList<List<Integer>>();
        }
        m = matrix.length;
        n = matrix[0].length;

        boolean[][] canP = new boolean[m][n];
        boolean[][] canA = new boolean[m][n];
        
        for(int i=0 ; i<m; i++){
            dfs(matrix, i, 0,canA);
            dfs(matrix, i, n-1,canP);
        }

        for(int j=0 ; j<n ; j++){
            dfs(matrix, 0, j, canA);
            dfs(matrix, m-1, j, canP);
        }

        List<List<Integer>> result = new ArrayList<List<Integer>>();

        for(int i=0 ; i<m; i++){
            for(int j=0 ; j<n ; j++){
                if(canA[i][j]&&canP[i][j]){
                    result.add(Arrays.asList(i,j));
                }
            }
        }

        return result;
    }

    public static void dfs(int[][] matrix, int i , int j, boolean[][] can){
        if(i<0 || i>=m || j<0 || j>= n || can[i][j]){
            return;
        }
        can[i][j] = true;
        for(int[] d:direction){
            int nexti = i+d[0];
            int nextj = j+d[1];
            if(nexti<0 || nexti>=m || nextj<0 || nextj>= n || matrix[i][j]> matrix[nexti][nextj]){
                continue;
            }
            dfs(matrix, nexti, nextj, can);
        }
    }
}
