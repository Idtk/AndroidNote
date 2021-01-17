package com.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class Leetcode452 {
    public static void exec(){
        int[][] points = new int[][]{new int[]{10,16},new int[]{2,8},new int[]{1,6},new int[]{7,12}};
        int result = findMinArrowShots(points);
        System.out.println("result: "+result);
    }

    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(o->o[0]));
        int count = 1;
        int end = points[0][1];
        // 其实这里更好是把1排除，不过不排除也没关系，初始的end肯定包含了第一个数组
        for(int[] point:points){
            if(end >= point[0]){
                continue;
            }
            count++;
            end = point[1];
        }
        return count;
    }
}
