package com.greedy;

public class Leetcode655 {
    public static void exec(){
        int[] nums =new int[]{3,4,2,3};
        boolean result = checkPossibility(nums);
        System.out.println("result: "+result);
    }

    public static boolean checkPossibility(int[] nums) {
        int cnt = 0;
        for(int i = 1;i<nums.length;i++){
            if(nums[i]>=nums[i-1]){
                continue;
            }
            cnt++;
            if(i-2>=0 && nums[i-2]>nums[i]){
                nums[i] = nums[i-1];
            }else{
                nums[i-1] = nums[i];
            }
            
        }
        return cnt<=1;
    }
}
