package notes.heaps;

import java.util.ArrayList;

public class Heap<E extends Comparable<E>> {

    private ArrayList<E> data;
    private int free;

    public Heap(int size) {
        data = new ArrayList<>(size);
        free = 0;
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
        int p = 0;
        int left = (2 * p) + 1;
        int right = (2 * p) + 2;

        while (right <= free) {
            int minChild = left;
            if (right != free - 1 && data.get(right).compareTo(data.get(left)) < 0)
                minChild = right;

            // minChild holds the index of the smallest of the two children
            if (data.get(p).compareTo(data.get(minChild)) < 0)
                break;

            swap(p, minChild);
            p = minChild;
            left = (2 * p) + 1;
            right = (2 * p) + 2;
        }
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