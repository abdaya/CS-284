package treaps;

import java.util.*;

public class Treap<E extends Comparable<E>> {

    private static class Node<F> {

        private int priority;
        private F data;
        private Node<F> left;
        private Node<F> right;

        public Node(F data, int priority) {
            if (data == null)
                throw new IllegalArgumentException();
            this.data = data;
            this.priority = priority;
            this.left = null;
            this.right = null;
        }

        /**
         * Performs a right rotation returning a reference to the root of the result.
         * Updates data and priority attributes as well as the left and right pointers
         * of the nodes involved in the rotation.
         * 
         * @return the root node of the result
         */
        public Node<F> rotateRight() {
            return null;
        }

        /**
         * Performs a left rotation returning a reference to the root of the result.
         * Updates data and priority attributes as well as the left and right pointers
         * of the nodes involved in the rotation.
         * 
         * @return the root node of the result.
         */
        public Node<F> rotateLeft() {
            return null;
        }

    }

    private Node<E> root;
    private Random priorityGenerator;

    public Treap() {

    }

    public Treap(long seed) {

    }


    public boolean add(E key) {
        return true;
    }

    public boolean add(E key, int priority) {
        return true;
    }

    public boolean delete(E key) {
        return true;
    }

    private boolean find(Node<E> root, E key) {
        return true;
    }

    public boolean find(E key) {
        return find(root, key);
    }

    public String toString() {
        return "";
    }
    
}