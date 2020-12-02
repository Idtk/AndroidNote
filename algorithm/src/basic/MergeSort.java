import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        int[] arr = { 19, 8, 7, 6, 5, 4, 13, 12, 1 };
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr) {
        int k = 1;
        int len = arr.length;
        while (k < len) {
            part(arr, k, len);
            k *= 2;
        }
    }

    public static void part(int[] arr, int k, int len) {
        int i = 0;
        while (i + 2 * k <= len) {
            mergeSort(arr, i, i + k - 1, i + 2 * k - 1);
            i += 2 * k;
        }

        if (i + k < len) {
            mergeSort(arr, i, i + k - 1, len-1);
        }
    }

    public static void mergeSort(int[] arr, int low, int mid, int high) {
        System.out.println(low+":"+mid+":"+high);
        int[] temp = new int[high - low + 1];
        int k = 0;
        int i = low;
        int j = mid + 1;
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid)
            temp[k++] = arr[i++];
        while (j <= high)
            temp[k++] = arr[j++];
        for (int l = 0; l < temp.length; l++) {
            arr[l + low] = temp[l];
        }
    }

    public static void swap(int[] arr, int i, int j) {
        if (i == j)
            return;
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }
}