package com.sort;

public class Leetcode215 {
    
    public static void exec(){
        int[] nums = new int[]{3,2,1,5,6,4};
        int result = findKthLargest(nums, 2);
        System.out.println("result: "+result);
    }

    public static int findKthLargest(int[] nums, int k) {
        int l = 0;
        int r = nums.length -1;
        int index = nums.length - k;
        while(l<r){
            int i = partition(nums, l, r);
            if(index == i){
                break;
            }else if(i<index){
                l = i+1;
            }else{
                r = i-1;
            }
        }
        // quickSort(nums, l, r);

        return nums[index];
    }

    public static void quickSort(int[] nums,int l ,int r){
        if(l<r){
            int i = partition(nums, l, r);
            quickSort(nums, l, i-1);
            quickSort(nums, i+1, r);
        }
    }

    public static int partition(int[] nums,int l ,int r){
        int i = l;
        int j = r;
        int temp = nums[i];
        while(i<j){
            while(i<j && nums[j]>=temp){
                j--;
            }
            while(i<j && nums[i]<=temp){
                i++;
            }
            if(i<j){
                swap(nums, i, j);
            }
        }
        swap(nums, i, l);
        return i;
    }


    public static void swap(int[] arr, int i, int j) {
        if (i == j)
            return;
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }

}
