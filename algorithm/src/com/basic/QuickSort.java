package com.basic;
import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int[] arr = { 19, 8, 7, 6, 5, 4, 13, 12, 1 };
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int i = quickSort(arr, low, high);
            sort(arr, low, i - 1);
            sort(arr, i + 1, high);
        }

    }

    public static int quickSort(int arr[], int low, int high) {
        int i = low;
        int j = high;
        int temp = arr[i];
        while (i < j) {
            while (i < j && arr[j] >= temp)
                j--;
            while (i < j && arr[i] <= temp)
                i++;
            if (i != j) {
                swap(arr, i, j);
            }
        }
        swap(arr, i, low);
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