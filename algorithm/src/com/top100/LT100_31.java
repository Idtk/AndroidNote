package com.top100;

import java.util.Arrays;

public class LT100_31 {

    public static void exec(){
        int[] nums = new int[]{1,2};
        nextPermutation(nums);
        System.out.println("res: "+Arrays.toString(nums));
    }

    public static void nextPermutation(int[] nums) {
        int i = nums.length-2;
        while(i>=0 && nums[i+1]<=nums[i]){
            i--;
        }

        if(i>=0){
            int j=nums.length-1;
            while(j>=0 && nums[j]<=nums[i]){
                j--;
            }
            swap(nums,i,j);         
        }

        reverse(nums,i+1,nums.length-1);
    }

    public static void swap(int[] nums,int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void reverse(int[] nums, int start, int end){
        int left = start;
        int right = end;
        while(left<right){
            swap(nums, left++, right--);
        }
    }
}
