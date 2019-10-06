package notes.lists;

import notes.pair.*;

public class SLL<E> {

    // Modularity and information hiding.
    // Nodes do not make sense to us as a top level class.
    public static class Node<F> {
        private F data;
        private Node<F> next;

        Node(F data) {
            this.data = data;
            this.next = null;
        }

        Node(F data, Node<F> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;

    SLL() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(E item) {
        head = new Node<>(item, head);
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        E temp = head.data;
        head = head.next;
        size--;
        return temp;
    }

    public void addLast(E item) {
        if (head == null) {
            this.addFirst(item);
        } else {
            Node<E> current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = new Node<E>(item);
            size++;
        }
    }

    public E removeLast() {
        if (head == null) { // empty list
            throw new IllegalStateException();
        }

        if (head.next == null) { // singleton list
            Node<E> temp = head;
            head = null;
            size--;
            return temp.data;
        }

        // list has two or more items
        Node<E> current = head;
        while (current.next.next != null) {
            current = current.next;
        }

        Node<E> temp = current;
        current.next = null;
        size--;
        return temp.data;
    }

    private void addAfter(Node<E> n, E item) {
        n.next = new Node<>(item, n.next);
        size++;
    }

    /**
     * Retrieves the i-th node of the linked-list
     * 
     * @param i Should be greater than or equal to 0 and less than the length of the
     *          list
     * @return
     */
    private Node<E> getNode(int i) {
        Node<E> current = head;

        for (int j = 0; j < i; j++) {
            current = current.next;
        }

        return current;
    }

    public E get(int i) {
        if (i < 0 || i > size) {
            throw new IllegalArgumentException();
        }
        return getNode(i).data;
    }

    public void add(int i, E item) {
        if (i < 0 || i > size) {
            throw new IllegalArgumentException();
        }

        if (i == 0) {
            addFirst(item);
        } else {
            addAfter(getNode(i - 1), item);
        }
    }

    public SLL<E> clone() {
        SLL<E> result = new SLL<>();
        result.size = size;
        Node<E> current = head;
        Node<E> last = new Node<E>(null);
        Node<E> dummy = last;

        while (current != null) {
            last.next = new Node<E>(current.data);
            last = last.next;
            current = current.next;
        }
        result.head = dummy.next;
        return result;
    }

    public void drop(int n) {
        if (n <= 0) {
            // nothing to drop
            return;
        }
        if (n >= size) {
            // drop all items in list
            head = null;
            size = 0;
            return;
        }
        // n>0 && n<size
        for (int i = 0; i < n; i++) {
            removeFirst();
        }
    }

    public SLL<E> take(int n) {
        if (n >= size) { // clone entire list
            return clone();
        }
        if (n <= 0) { // return an empty list
            return new SLL<E>();
        }
        // n>0 and n<size
        // Look at the code for clone
        SLL<E> result = new SLL<E>();
        Node<E> last = new Node<E>(null);
        Node<E> dummy = last;
        Node<E> current = head;
        for (int i = 0; i < n; i++) {
            last.next = new Node<E>(current.data);
            last = last.next;
            current = current.next;
        }
        result.head = dummy.next;
        result.size = n;
        return result;
    }

    public void remove_nulls() {
        if (head == null) {
            return;
        }
        while (head != null && head.data == null) {
            removeFirst();
        }
        if (head == null) {
            return;
        }
        // List is non-empty and does not start with a null item
        Node<E> current = head;
        while (current.next != null) {
            if (current.next.data == null) {
                current.next = current.next.next;
                size--;
            } else {
                current = current.next;
            }
        }
    }

    public Pair<SLL<E>, SLL<E>> splitAt(int n) {
        SLL<E> li = clone();
        li.drop(n);
        return new Pair<SLL<E>, SLL<E>>(take(n), li);
    }

    public SLL<Pair<E, E>> zip(SLL<E> l2) {
        SLL<Pair<E, E>> result = new SLL<>();
        Node<E> current1 = head;
        Node<E> current2 = l2.head;
        // Node<E> dummy = last;
        // Node<E> current = head;

        while (current1 != null && current2 != null) {
            result.addLast(new Pair<E, E>(current1.data, current2.data));
        }
        return result;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public String toString() {
        Node<E> current = head;
        StringBuilder s = new StringBuilder();
        s.append("[");
        while (current != null) {
            s.append(current.data == null ? "null," : current.data.toString() + ",");
            current = current.next;
        }
        s.append("]");
        return s.toString();
    }

}