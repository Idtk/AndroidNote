package com.doublePointer;

import java.util.Arrays;

public class Leetcode88 {
    public static void exec(){
        int[] nums1 = new int[]{1,2,3,0,0,0};
        int[] nums2 = new int[]{2,5,6};
        merge(nums1, 3, nums2, 3);
        System.out.println("result: "+Arrays.toString(nums1));
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m-1;
        int index2 = n-1;
        int merge = m+n-1;
        while(index1 >=0 || index2 >= 0){
            if(index1 < 0){
                nums1[merge--] = nums2[index2--];
            }else if(index2 < 0){
                nums1[merge--] = nums1[index1--];
            }else if(nums1[index1]<=nums2[index2]){
                nums1[merge--] = nums2[index2--];
            }else{
                nums1[merge--] = nums1[index1--];
            }
        }
    }
}
