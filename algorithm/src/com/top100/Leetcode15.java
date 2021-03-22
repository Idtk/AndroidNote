package com.top100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Leetcode15 {

    public static void exec(){
        List<List<Integer>> res = threeSum(new int[]{-1,0,1,2,-1,-4});
        System.out.println("res: "+res);
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for(int i=0; i<nums.length-2; i++){
            if(nums[i]>0) break;
            if(i>0 && nums[i]==nums[i-1])continue;
            int l = i+1;
            int r = nums.length-1;
            while(l<r){
                int sum = nums[i]+nums[l]+nums[r];
                if(sum<0){
                    while(l<r && nums[l]==nums[++l]);
                }else if(sum>0){
                    while(l<r && nums[r]==nums[--r]);
                }else{
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[i]);
                    list.add(nums[l]);
                    list.add(nums[r]);
                    res.add(list);
                    while(l<r && nums[l]==nums[++l]);
                    while(l<r && nums[r]==nums[--r]);
                }
            }
        }
        return res;
    }
}
