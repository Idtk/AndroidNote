package com.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Leetcode347 {
    public static void exec(){
        int[] nums = new int[]{1,1,1,2,2,3};
        int[] result = topKFrequent(nums, 2);
        System.out.println("result: "+Arrays.toString(result));
    }

    public static int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int num:nums){
            if(map.containsKey(num)){
                map.put(num, map.get(num)+1);
            }else{
                map.put(num, 1);
            }
        }

        List<Integer>[] list = new List[map.keySet().size()+1];
        for(int key:map.keySet()){
            int i = map.get(key);
            if(list[i] == null){
                list[i] = new ArrayList<>();
            }
            list[i].add(key);
        }

        List<Integer> topK = new ArrayList<>();
        for(int i = map.keySet().size();topK.size()<k;i--){
            if(list[i] == null) continue;
            topK.addAll(list[i]);
        }

        int[] res = new int[k];
        for(int i = 0; i<k;i++){
            res[i] = topK.get(i);
        }

        return res;
    }
}
