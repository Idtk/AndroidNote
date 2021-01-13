package com.basic;
import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int []arr = {19,8,7,6,5,4,13,12,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static void exec() {
        int []arr = {19,8,7,6,5,4,13,12,1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        for (int i = arr.length / 2; i >= 1; i--) {
            adjustHeap(arr, i, arr.length);
        }

        for (int j = arr.length - 1; j >= 1; j--) {
            swap(arr, 0, j);
            adjustHeap(arr, 0, j);
        }

    }

    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i];
        for(int k = 2*i + 1; k < length ; k = 2*k +1){
            if(k+1<length && arr[k]<arr[k+1]){
                k++;
            }
            if(arr[k]>temp){
                // arr[i] = arr[k];
                swap(arr,i,k);
                i = k;
            }
        }
        // arr[i] = temp;

    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j)
            return;
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}
