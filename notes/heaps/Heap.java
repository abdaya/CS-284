package notes.heaps;

import java.util.*;

public class Heap<E extends Comparable<E>> {

    private List<E> data;
    private int free;

    public Heap(int size) {
        data = new ArrayList<>(size);
        free = 0;
    }

    public Heap(ArrayList<E> l) {
        data = l;
        free = 0;
        heapify();
    }

    private void heapify() {
        for (int i = data.size() / 2; i > 0; i--)
            percolateDown(i);
    }
    
    private void percolateDown(int i) {
        int parent = i;
        int left = (2 * parent) + 1;
        int right = (2 * parent) + 2;

        while (right <= data.size()) {
            int minChild = left;
            if (right != data.size() && data.get(left).compareTo(data.get(right)) > 0)
                minChild = right;

            if (data.get(parent).compareTo(data.get(minChild)) < 0)
                break;
            
            swap(parent, minChild);
            parent = minChild;
            left = (2 * parent) + 1;
            right = (2 * parent) + 2;
        }

    }
 
    public void add(E item) {

        data.add(free, item);

        int p = free;
        int parent = (free - 1) / 2;
        while (p >= 0 && data.get(parent).compareTo(item) > 0) {
            swap(parent, p);
            p = parent;
            parent = (p - 1) / 2;
        }
        free++;
    }

    public E remove() {
        if (free == 0)
            throw new IllegalStateException();

        E temp = data.get(0);
        data.set(0, data.remove(free - 1));
        
        percolateDown(0);

        free--;
        return temp;
    }

    public E top() {
        if (free == 0)
            throw new IllegalStateException();
        return data.get(0);
    }

    private void swap(int i, int j) {
        E temp = this.data.get(i);
        this.data.set(i, data.get(j));
        this.data.set(j, temp);
    }

    public String toString() {
        return data.toString();
    }

    public static void main(String[] args) {
        Heap<Integer> h = new Heap<>(100);
        int[] l = { 35, 27, 14, 48, 12, 9, 64 };
        // Set<Integer[]> s = new HashSet<>();
        // s.add(new Integer[] { 1, 2 });
        // System.out.println(s);
        // System.out.println(s.add(new Integer[] { 1, 2 }));
        // System.out.println(s);

        for (int i : l) {
            h.add(i);
        }
        System.out.println(h);
        System.out.println(h.remove());
        System.out.println(h);

    }

}