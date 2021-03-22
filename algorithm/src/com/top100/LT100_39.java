package com.top100;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class LT100_39 {

    public static void exec(){
        List<List<Integer>> res = combinationSum(new int[]{2,3,6,7}, 7);
        System.out.println("res: "+res);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if(candidates.length==0){
            return res;
        }
        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>();
        dfs(0, target, path, res, candidates);
        return res;
    }

    public static void dfs(int start,int target,Deque<Integer> path,List<List<Integer>> res,int[] candidates){
        if(target == 0){
            res.add(new ArrayList<Integer>(path));
            return;
        }
        for(int i=start;i<candidates.length;i++){
            if(target-candidates[i]<0){
                return;
            }
            path.addLast(candidates[i]);
            dfs(i, target-candidates[i], path, res, candidates);
            path.removeLast();
        }
    }
}
