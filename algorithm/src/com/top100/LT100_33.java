package com.top100;

public class LT100_33 {
    public static void exec(){
        int[] nums = new int[]{3,1};
        int res = search(nums, 1);
        System.out.println("res: "+res);
    }

    public static int search(int[] nums, int target) {
        int len = nums.length;
        if(len == 0){
            return -1;
        }
        
        int l=0;
        int r=len-1;
        while(l<=r){
            int mid = l+(r-l)/2;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[0]<=nums[mid]){
                if(target>=nums[0] && target<nums[mid]){
                    r = mid-1;
                }else{
                    l = mid+1;
                }
            }else{
                if(target>nums[mid] && target<=nums[len-1]){
                    l = mid+1;
                }else{
                    r = mid-1;
                }
            }
        }
        return -1;
    }
}
