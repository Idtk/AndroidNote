package com.greedy;


public class Leetcode605 {
    
    public static void exec(){
        int[] flowerbed = new int[]{1,0,0,0,1};
        boolean result = canPlaceFlowers(flowerbed, 1);
        System.out.println("result: "+result);
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        for(int i = 0;i<flowerbed.length;i++){
            if(i==0 && flowerbed[i] ==0 && flowerbed[i+1]==0){
                n--;
                flowerbed[i] = 1;
            }else if(i == flowerbed.length-1 && flowerbed[i]==0 && flowerbed[i-1]==0){
                n--;
                flowerbed[i] = 1;
            }else if(i>0 && i < flowerbed.length-1 && flowerbed[i+1]==0 && flowerbed[i]==0 && flowerbed[i-1]==0){
                n--;
                flowerbed[i] = 1;
            }
        }
        return n<1;
    }

    public static boolean canPlaceFlowers2(int[] flowerbed, int n) {
        for(int i = 0;i<flowerbed.length;i++){
            if(flowerbed[i] ==1){
                continue;
            }
            int pre = i==0?0:flowerbed[i-1];
            int next = i==flowerbed.length-1?0:flowerbed[i+1];
            if(pre == 0 && next == 0){
                n--;
                flowerbed[i] = 1;
            }
        }
        return n<1;
    }
}
