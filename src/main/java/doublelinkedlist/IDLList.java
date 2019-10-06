package doublelinkedlist;

import java.util.ArrayList;

public class IDLList<E> {

    private class Node<F> {

        private F data;
        private Node<F> next;
        private Node<F> prev;

        /**
         * Creates a node with no next or previous pointers.
         * 
         * @param data
         */
        public Node(F data) {
            this.data = data;
            next = null;
            prev = null;
        }

        /**
         * Creates a node with next and previous pointers.
         * 
         * @param data
         * @param next
         * @param prev
         */
        public Node(F data, Node<F> next, Node<F> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

    }

    private Node<E> head;
    private Node<E> tail;
    private int size;
    private ArrayList<Node<E>> indices;

    /**
     * Creates an empty double-linked list with fast accessing.
     */
    public IDLList() {
        head = null;
        tail = null;
        size = 0;
        indices = new ArrayList<Node<E>>();
    }

    /**
     * Adds an element at the head
     * 
     * @param elem the element to be added
     * @return
     */
    public boolean add(E elem) {
        if (head == null) { // Empty list
            head = new Node<E>(elem, null, null);
            tail = head;
        } else if (head == tail) { // Singleton list
            head = new Node<E>(elem, tail, null);
            tail.prev = head;
        } else {
            head = new Node<E>(elem, head, null);
            head.next.prev = head;
        }

        indices.add(0, head);
        size++;
        return true;
    }

    /**
     * Adds an element at position index (counting from wherever head is). It uses
     * the index for fast access. It always returns true.
     * 
     * @throws IndexOutofBoundsException
     * @param index
     * @param elem
     * @return
     */
    public boolean add(int index, E elem) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) { // add to the beginning of the list
            add(elem);
        } else {
            // somewhere between the beginning and the end
            Node<E> current = indices.get(index);
            Node<E> newCurrent = new Node<>(elem, current, current.prev);
            current.prev.next = newCurrent;
            current.prev = newCurrent;
            size++;
            indices.add(index, newCurrent);
        }
        return true;
    }

    /**
     * Adds elem as the new last element of the list (i.e. at the tail). It always
     * returns true.
     * 
     * @param elem
     * @return
     */
    public boolean append(E elem) {
        if (head == null) { // empty list
            head = new Node<E>(elem, null, null);
            tail = head;
            size++;
            return indices.add(head);
        }

        if (head == tail) { // singleton list
            tail = new Node<E>(elem, null, head);
            head.next = tail;
            size++;
            return indices.add(tail);
        }
        tail.next = new Node<E>(elem, null, tail);
        tail = tail.next;
        size++;
        return indices.add(tail);
    }

    /**
     * Returns the object at position index from the head. It uses the index for
     * fast access.
     * 
     * @param index
     * @return
     */
    public E get(int index) {
        return indices.get(index).data;
    }

    /**
     * Returns the object at the head.
     * 
     * @return
     */
    public E getHead() {
        if (head == null)
            throw new IllegalStateException();
        return head.data;
    }

    /**
     * Returns the object at the tail.
     * 
     * @return
     */
    public E getLast() {
        if (tail == null)
            throw new IllegalStateException();
        return tail.data;
    }

    /**
     * Returns the size of the list.
     * 
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Removes and returns the element at the head
     * 
     * @throws IllegalStateException if there is no such element.
     * @return
     */
    public E remove() {
        if (head == null) {
            throw new IllegalStateException();
        }

        if (head == tail) {
            Node<E> temp = head;
            head = null;
            tail = null;
            size--;
            indices.clear();
            return temp.data;
        }

        Node<E> temp = head;
        head = head.next;
        indices.remove(0);
        size--;
        return temp.data;
    }

    /**
     * Removes and returns the element at the tail.
     * 
     * @throws IllegalStateException is there is no such element
     * @return
     */
    public E removeLast() {
        if (head == null) {
            throw new IllegalStateException();
        }

        if (head == tail) {
            Node<E> temp = tail;
            head = null;
            tail = null;
            size = 0;
            indices.clear();
            return temp.data;
        }

        Node<E> temp = tail;
        tail = tail.prev;
        tail.next = null;
        indices.remove(size - 1);
        size--;
        return temp.data;
    }

    /**
     * Removes and returns the element at the index.
     * 
     * @throws IllegalStateException if there is no such element.
     * @param index
     * @return
     */
    public E removeAt(int index) {
        if (index < 0 || index > size) {
            throw new IllegalStateException("index: " + index);
        }

        if (index == 0) { // remove first element
            return remove();
        }

        if (index == size - 1) { // remove last element
            return removeLast();
        }

        // remove an element between the first and the last
        Node<E> current = indices.remove(index);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return current.data;
    }

    /**
     * Removes the first occurrence of the element is in the list and returns true.
     * Returns false if the element is not in the list.
     * 
     * @param elem
     * @return
     */
    public boolean remove(E elem) {

        if (elem.equals(head.data)) { // remove the first element
            remove();
            return true;
        }

        if (elem.equals(tail.data)) { // remove the last element
            removeLast();
            return true;
        }

        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(elem)) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                indices.remove(index);
                size--;
                return true;
            }
            current = current.next;
            index++;
        }
        return false;
    }

    public String toString() {
        Node<E> current = head;
        StringBuilder s = new StringBuilder();
        s.append("[ ");
        while (current != null) {
            s.append(current.data.toString() + ", ");
            current = current.next;
        }
        s.append("]");
        return s.toString();
    }

}