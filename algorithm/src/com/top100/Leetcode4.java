package com.top100;

public class Leetcode4 {
    
    public static void exec(){
        int[] nums1 = new int[]{1,3};
        int[] nums2 = new int[]{2};
        double res = findMedianSortedArrays(nums1, nums2);
        System.out.println("result: "+res);
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length;
        int l2 = nums2.length;
        if(l1>l2){
            return findMedianSortedArrays(nums2, nums1);
        }
        int k = (l1+l2+1)/2;
        int left = 0;
        int right = l1;
        while(left<right){
            int m1 = left+(right-left)/2;
            int m2 = k-m1;
            if(nums1[m1]<nums2[m2-1]){
                left = m1+1;
            }else{
                right = m1;
            }
        }

        int m1 = left;
        int m2 = k-left;
        int c1 = Math.max(m1<=0?Integer.MIN_VALUE:nums1[m1-1], m2<=0?Integer.MIN_VALUE:nums2[m2-1]);
        if((l1+l2)%2==1){
            return c1;
        }

        int c2 = Math.min(m1>=l1?Integer.MAX_VALUE:nums1[m1], m2>=l2?Integer.MAX_VALUE:nums2[m2]);
        return (c1+c2)*0.5;
    }

}
