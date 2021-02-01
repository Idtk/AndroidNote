package com.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode406 {
    public static void exec(){
        int[][] people = new int[][]{new int[]{7,0},new int[]{4,4},new int[]{7,1},new int[]{5,0},new int[]{6,1},new int[]{5,2}};
        int[][] result = reconstructQueue(people);
        System.out.println("result: "+ Arrays.toString(result));
    }

    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people,(a,b)->a[0] != b[0] ? b[0]-a[0]:a[1]-b[1]);
        List<int[]> list = new ArrayList<>();
        for(int[] p : people){
            list.add(p[1], p);
        }

        return list.toArray(new int[list.size()][2]);
    }
}
