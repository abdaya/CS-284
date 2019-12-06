package notes.sorts;

import java.util.Arrays;

import java.util.PriorityQueue;

public class HeapSort {

    public static void sortH(int[] arr) {
        PriorityQueue<Integer> h = new PriorityQueue<>();
        for (int i : arr)
            h.add(i);

        for (int i = 0; i < arr.length; i++)
            arr[i] = h.poll();
    }
    
    public static void percolateDown(int[] arr, int i, int end) {
        int parent = i;
        int left = (2 * parent) + 1;
        int right = (2 * parent) + 2;
        while (right <= end) {
            int maxChild = left;
            if (right < end && arr[right] > arr[left])
                maxChild = right;

            if (arr[parent] > arr[maxChild])
                break;

            swap(arr, parent, maxChild);
            parent = maxChild;
            left = (2 * parent) + 1;
            right = (2 * parent) + 2;
        }
    }

    public static void sort(int[] arr) {

        // build max heap O(N)
        for (int i = (arr.length / 2) - 1; i >= 0; i--) 
            percolateDown(arr, i, arr.length);

        // shrink heap O(N log N)
        for (int end = arr.length - 1; end > 0; end--) {
            swap(arr, 0, end);
            percolateDown(arr, 0, end);
        }
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        // int[] arr = { 74, 66, 89, 6, 39, 29, 76, 32, 18, 28, 37, 26, 20 };
        int[] arr = { 8, 4, 1, 6 };

        // int[] arr = { 10,6,9,5,3,8,7,4,2,1 };
        System.out.println(Arrays.toString(arr));
        HeapSort.sort(arr);
        // System.out.println(Arrays.toString(arr));
        // HeapSort.sortH(arr);
        System.out.println(Arrays.toString(arr));
    }
    
}