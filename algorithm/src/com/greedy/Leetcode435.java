package com.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class Leetcode435 {
    
    public static void exec(){
        int[][] intervals = new int[][]{new int[]{1,2},
        new int[]{2,3}, new int[]{3,4}, new int[]{1,3}};
        int result = eraseOverlapIntervals(intervals);
        System.out.println("result: "+result);
    }

    public static int eraseOverlapIntervals(int[][] intervals) {
        int cnt = 1;
        int end = intervals[0][1];
        Arrays.sort(intervals,Comparator.comparingInt(o->o[1]));
        for(int i = 1; i<intervals.length ; i++){
            if(end > intervals[i][0]){
                continue;
            }
            cnt++;
            end = intervals[i][1];
        }
        return intervals.length - cnt;
    }

}
