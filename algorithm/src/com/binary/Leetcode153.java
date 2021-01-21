package com.binary;

public class Leetcode153 {
    public static void exec(){
        int[] nums =new int[]{3,4,5,1,2};
        int result = findMin(nums);
        System.out.println("result: "+result);
    }

    public static int findMin(int[] nums) {
        int l = 0;
        int r = nums.length -1;
        while(l<r){
            int m = l + (r-l)/2;
            int end = nums[r];
            if(end<nums[m]){
                l = m+1;
            }else{
                r = m;
            }
        }
        return nums[r];
    }
}
