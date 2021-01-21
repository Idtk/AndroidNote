package com.binary;

public class Leetcode540 {
    public static void exec(){
        int[] nums = new int[]{1,1,2,3,3,4,4,8,8};
        int result = singleNonDuplicate(nums);
        System.out.println("result: "+result);
    }
    

    public static int singleNonDuplicate(int[] nums) {
        int l = 0, r = nums.length -1;
        while(l<r){
            int m = l + (r-l)/2;
            int num = nums[m];
            int pre = nums[m-1];
            int next = nums[m+1];
            int oddEven = (m-l)%2;
            if(num == next){
                if(oddEven==0){
                    l = m+2;
                }else{
                    r = m-1;
                }
            }else if(num == pre){
                if(oddEven==0){
                    r = m-2;
                }else{
                    l = m+1;
                }
            }else{
                return num;
            }
        }

        return nums[l];
    }
}

