package com.binary;

import java.util.Arrays;

public class Leetcode34 {
    public static void exec(){
        int[] nums = new int[]{2,2};
        int[] result = searchRange(nums, 1);
        System.out.println("result: "+Arrays.toString(result));
    }

    public static int[] searchRange(int[] nums, int target) {
        int l = binarySearch(nums, target, true);
        int r = binarySearch(nums, target, false)-1;
        if(l<=r && r< nums.length && nums[l] == target && nums[r] == target){
            return new int[]{l,r};
        }
        return new int[]{-1,-1};
    }

    public static int binarySearch(int[] nums, int target, boolean lower){
        int l = 0;
        int r = nums.length -1;
        int ans = nums.length;
        while(l<=r){
            int m = l + (r-l)/2;
            if(nums[m]>target || (lower && nums[m]>=target)){
                r = m -1;
                ans = m;
            }else{
                l = m+1;
            }
        }

        return ans;

    }
}
