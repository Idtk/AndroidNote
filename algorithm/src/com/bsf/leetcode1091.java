package com.bsf;

import java.util.LinkedList;
import java.util.Queue;


public class leetcode1091 {
    public static void exec(){
        int[][] grid = new int[3][3];
        grid[0][0] = 0;
        grid[0][1] = 1;
        grid[0][2] = 1;

        grid[1][0] = 0;
        grid[1][1] = 1;
        grid[1][2] = 1;

        grid[2][0] = 0;
        grid[2][1] = 0;
        grid[2][2] = 0;
        
        int result = shortestPathBinaryMatrix(grid);
        System.out.println("result: "+result);
    }

    public static int shortestPathBinaryMatrix(int[][] grid) {
        if(grid == null || grid.length ==0 || grid[0].length == 0){
            return -1;
        }

        int row = grid.length;
        int col = grid[0].length;
        int[][] direction = new int[][]{{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0});
        int path = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            path++;
            while(size -- >0){
                int[] point = queue.poll();
                if(grid[point[0]][point[1]] == 1){
                    continue;
                }
                if(point[0] == row-1 && point[1] == col-1){
                    return path;
                }
                // 打标记,不可再选中
                grid[point[0]][point[1]] = 1;
                for(int[] d:direction){
                    int r = point[0]+d[0];
                    int c = point[1]+d[1];
                    if(r <0 || r>row-1 || c<0 || c> col-1){
                        continue;
                    }
                    queue.add(new int[]{r,c});
                }
            }
        }

        return -1;
    }
}
