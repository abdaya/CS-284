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
            if (left == null)
                throw new IllegalStateException();
            Node<F> root = left;
            this.left = root.right;
            root.right = this;
            return root;
        }

        /**
         * Performs a left rotation returning a reference to the root of the result.
         * Updates data and priority attributes as well as the left and right pointers
         * of the nodes involved in the rotation.
         * 
         * @return the root node of the result.
         */
        public Node<F> rotateLeft() {
            if (right == null)
                throw new IllegalStateException();
            Node<F> root = right;
            this.right = root.left;
            root.left = this;
            return root;
        }

    }

    private Node<E> root;
    private Random priorityGenerator;

    /**
     * Creates an empty Treap.
     */
    public Treap() {
        root = null;
        priorityGenerator = new Random();
    }

    /**
     * Creates an empty Treap with a given seed.
     * @param seed
     */
    public Treap(long seed) {
        root = null;
        priorityGenerator = new Random(seed);
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