package notes.sorts;

import java.util.Arrays;

public class InsertionSort {
    
    public static void sort(int[] arr) {
        int index = 1;
        while (index < arr.length) {
            int curr = index;
            System.out.println(Arrays.toString(arr));
            while (curr > 0) {
                if (arr[curr - 1] > arr[curr]) {
                    swap(arr, curr - 1, curr);
                    curr--;
                } else {
                    break;
                }
            }
            index++;
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = { 8, 1, 4, 5, 9, 2, 6, 5 };
        System.out.println(Arrays.toString(arr));
        InsertionSort.sort(arr);
        System.out.println(Arrays.toString(arr));        
    }
}