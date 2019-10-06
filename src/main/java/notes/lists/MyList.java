package notes.lists;

import java.util.Arrays;

public class MyList<E> {

    // Class constants
    private static final int INITIAL_CAPACITY = 100;

    // Data fields
    private E[] data;
    private int free;

    // Constructor
    MyList() {
        data = (E[]) new Object[INITIAL_CAPACITY];
        free = 0;
    }

    // Methods

    public boolean add(E item) {
        if (free == data.length) {
            resize();
        }
        data[free] = item;
        free++;
        return true;
    }

    public boolean add(E item, int index) {
        if (index < 0 || index > free) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = free; i < index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        free++;
        return true;
    }

    private void resize() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public void remove(E item) {
        int index = find(item);
        if (index == free) {
            throw new IllegalArgumentException();
        } else {
            for (int i = index; i < free - 1; i++) {
                data[i] = data[i + 1];
            }
            free--;
        }
    }

    public int find(E item) {
        int i = 0;
        while (i < free && !item.equals(data[i])) {
            i++;
        }
        return i;
    }

    public boolean member(E item) {
        int i = 0;
        // boolean found = true;
        // while (!found && i < free) {
        // found = found || item.equals(data[i]);
        // i++;
        // }
        // return found;

        while (i < free && !item.equals(data[i])) {
            i++;
        }
        return i < free;
    }

    public int size() {
        return free;
    }

    public E get(int index) {
        if (index < 0 || index > free) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return data[index];
    }

    public String toString() {
        String s = "[ ";
        if (free == 0) {
            s = s + " ]";
        } else {
            for (int i = 0; i < free - 1; i++) {
                s = s + data[i].toString() + ", ";
            }
            s = s + data[free - 1].toString() + " ]";
        }

        return s;
    }

    public static void main(String[] args) {

        MyList<Integer> li = new MyList<>();
        for (int i = 0; i < 20; i++) {
            li.add(i);
        }
        li.remove(3);
        System.out.println(li);
        // li.add(1);
        // li.add(2);
        // li.add(3);

        MyList<String> ls = new MyList<>();
        ls.add("h");
        ls.add("e");
        ls.add("l");
        ls.add("o");
        ls.add("l", 2);

        // System.out.println(li);
        // System.out.println(ls);

    }

}