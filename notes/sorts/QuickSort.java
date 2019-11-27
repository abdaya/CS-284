package notes.sorts;

import java.util.*;

public class QuickSort {


    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);
            System.out.println(Arrays.toString(arr) + "\tpivot: " + pivot);
            sort(arr, left, pivot - 1);
            sort(arr, pivot + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = left;
        do {
            while (left < arr.length && arr[left] <= arr[pivot])
                left++;
            while(arr[right] > arr[pivot]) 
                right--;

            if (left < right)
                swap(arr, left, right);
            
        } while (left < right);

        swap(arr, pivot, right);
        return right;
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = { 44, 75, 23, 43, 55, 12, 64, 77, 33 };
        System.out.println((Arrays.toString(arr)));
        // partition(arr, 0, arr.length - 1);
        sort(arr);
        System.out.println((Arrays.toString(arr)));
    }
    
}