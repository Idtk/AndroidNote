package com.dfs;

public class leetcode130 {
    public static void exec(){
        char[][] board = new char[][]{
            new char[]{'o','o'},
            new char[]{'o','o'},
        };
        // char[][] board = new char[][]{
        //     new char[]{'x','x','x','x'},
        //     new char[]{'x','o','o','x'},
        //     new char[]{'x','x','o','x'},
        //     new char[]{'x','o','x','x'}
        // };
        solve(board);
        System.out.println("result");
    }

    private static int m,n;

    private static int[][] direction = new int[][]{
        new int[]{0,-1},
        new int[]{1,0},
        new int[]{0,1},
        new int[]{-1,0}
    };

    public static void solve(char[][] board) {
        if(board.length == 0 || board[0].length == 0){
            return;
        }
        m = board.length;
        n = board[0].length;
        for(int i = 0; i<m ;i++){
            dfs(board, i, 0);
            dfs(board, i, n-1);
        }
        for(int j=0 ; j<n ; j++){
            dfs(board, 0, j);
            dfs(board, m-1, j);
        }

        for(int i = 0; i<m ;i++){
            for(int j=0 ; j<n ; j++){
                if(board[i][j] == 'o'){
                    board[i][j] = 'x';
                }else if(board[i][j] == '#'){
                    board[i][j] = 'o';
                }
            }
        }
    }

    public static void dfs(char[][] board,int i, int j){
        if(i<0 || i>=m || j<0 || j>=n || board[i][j] != 'o'){
            return;
        }
        board[i][j] = '#';
        for(int[] d:direction){
            dfs(board, i+d[0], j+d[1]);
        }
    }
}
