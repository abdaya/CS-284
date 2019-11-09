package notes.lists;

import java.util.ArrayList;
import java.util.HashMap;

import notes.pair.Pair;

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

    public SLL() {
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

        E temp = current.data;
        current.next = null;
        size--;
        return temp;
    }

    public void append(E item) {
        if (isEmpty()) {
            head = new Node<E>(item);
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<E>(item);
        }
        size++;
    }

    public void append(SLL<E> list) {
        if (isEmpty()) {
            head = list.head;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = list.head;
        }
        size++;
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
        // n > 0 && n < size
        int i = 0;
        while (i < n) {
            removeFirst();
            i++;
        }
    }

    public SLL<E> take(int n) {
        if (n >= size) { // clone entire list
            return clone();
        }
        if (n <= 0) { // return an empty list
            return new SLL<E>();
        }
        // n > 0 and n < size
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

    public void reverse() {
        if (head == null) { // empty list
            throw new IllegalStateException();
        }

        if (head.next == null) { // singleton list
            return;
        }

        Node<E> last = head;
        Node<E> current = head.next;
        last.next = null;
        while (current != null) {
            Node<E> temp = current.next;
            current.next = last;
            last = current;
            current = temp;
        }

        head = last;
    }

    public boolean containsCycle() {
        if (head == null || head.next == null) {
            return false;
        }

        Node<E> fast = head;
        Node<E> slow = head.next;
        while (fast != slow && fast != null) {
            fast = fast.next != null ? fast.next.next : null;
            slow = slow.next;
        }
        return fast == slow;
    }

    public boolean containsCycleBad() {
        if (head == null || head.next == null)
            return false;

        HashMap<Node<E>, Boolean> map = new HashMap<>();
        Node<E> current = head;
        while (!map.containsKey(current) && current != null) {
            map.put(current, true);
            current = current.next;
        }
        return current != null;
    }

    public void printReverse() {
        Node<E> current = head;

        System.out.print("[");
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size() - 1 - i; j++) {
                current = current.next;
            }
            System.out.print(current.data + ",");
            current = head;
        }
        System.out.println("]");

    }

    public void delete(Node<E> current) {
        current.data = current.next.data;
        current.next = current.next.next;
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
        Node<Pair<E, E>> last = new Node<>(null);
        Node<Pair<E, E>> dummy = last;

        while (current1 != null && current2 != null) {
            last.next = new Node<>(new Pair<>(current1.data, current2.data));
            last = last.next;
            current1 = current1.next;
            current2 = current2.next;
        }

        result.head = dummy.next;
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

    public static SLL<Integer> add(SLL<Integer> sll1, SLL<Integer> sll2) {

        Node<Integer> current1 = sll1.head;
        Node<Integer> current2 = sll2.head;

        Node<Integer> last = new Node<>(null);
        Node<Integer> dummy = last;

        int carry = 0;

        while (current1 != null && current2 != null) {
            int sum = current1.data + current2.data + carry;
            if (sum < 10) {
                last.next = new Node<>(sum);
                carry = 0;
            } else {
                carry = 1;
                last.next = new Node<>(sum - 10);
            }

            last = last.next;

            current1 = current1.next;
            current2 = current2.next;
        }

        last.next = current1;
        last.next = current2;

        SLL<Integer> result = new SLL<>();
        result.head = dummy.next;
        return result;
    }

    public static void main(String[] args) {
        // SLL<Integer> s1 = new SLL<>();
        // SLL<Integer> s2 = new SLL<>();
        // for (int i = 6; i < 9; i++) {
        // s1.addFirst(i);
        // s2.addFirst(i);
        // }
        // for (int i = 5; i < 7; i++) {
        // s2.addFirst(i);
        // }
        // System.out.println(s1);
        // System.out.println(s2);
        // System.out.println(SLL.add(s1, s2));
        SLL<Integer> sll = new SLL<>();
        for (int i = 0; i < 12; i++) {
            sll.addFirst(i);
        }
        System.out.println(sll);
        sll.printReverse();
        // // System.out.println(sll);
        // Node<Integer> node = sll.getNode(2);
        // System.out.println(node.data);
        // sll.delete(node);
        // System.out.println(sll);

    }
}