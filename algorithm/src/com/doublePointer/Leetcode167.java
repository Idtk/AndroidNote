package com.doublePointer;

import java.util.Arrays;

public class Leetcode167 {
    public static void exec(){
        int[] numbers = new int[]{2,7,11,15};
        int[] result = twoSum(numbers, 9);
        System.out.println("result: "+Arrays.toString(result));
    }

    public static int[] twoSum(int[] numbers, int target) {
        int l = 0;
        int r = numbers.length - 1;
        while(l<r){
            int sum = numbers[l] + numbers[r];
            if(sum<target){
                l++;
            }
            if(sum>target){
                r--;
            }
            if(sum == target){
                break;
            }
        }

        return new int[]{l,r};
    }
}
