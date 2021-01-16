package com.sort;

import java.util.Arrays;

public class Leetcode75 {
    public static void exec(){
        int[] nums = new int[]{2,0,2,1,1,0};
        sortColors(nums);
        System.out.println("result: "+Arrays.toString(nums));
    }

    public static void sortColors(int[] nums) {
        int p0 = 0;
        int p2 = nums.length -1;
        for(int i = 0; i<=p2 ;i++){
            while(i<=p2 && nums[i] == 2){
                swap(nums, i, p2);
                p2--;
            }
            while(i>=p0 && nums[i] == 0){
                swap(nums, i, p0);
                p0++;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j)
            return;
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}
