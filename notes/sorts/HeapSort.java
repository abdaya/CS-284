package notes.sorts;

public class HeapSort {

    public static void sort(int[] arr) {

        // build max heap
        for (int i = 1; i < arr.length; i++) {
            int child = i;
            int parent = (child - 1) / 2;
            while (child >= 0 && arr[child] > arr[parent]) {
                swap(arr, child, parent);
                child = parent;
                parent = (child - 1) / 2;
            }
        }

        // shrink heap
        for(int end = arr.length - 1; end > 0; end--) {
            swap(arr, 0, end);

            // re-heap
            int parent = 0;
            int left = (2 * parent) + 1;
            int right = (2 * parent) + 2;

            while (right <= end) {

                int maxChild = left;
                if(right < end && arr[right] > arr[left]) 
                    maxChild = right;

                if (arr[parent] > arr[maxChild])
                    break;
                
                swap(arr, parent, maxChild);
                parent = maxChild;
                left = (2 * parent) + 1;
                right = (2 * parent) + 2;
            }
        }
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        int[] arr = { 74, 66, 89, 6, 39, 29, 76, 32, 18, 28, 37, 26, 20 };
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
        HeapSort.sort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ", ");
        }
    }
    
}