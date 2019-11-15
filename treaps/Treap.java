package treaps;

import java.util.*;
import java.lang.reflect.Method;

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

        public Node<F> rotateDown() {
            if (left != null && right != null)
                return (left.priority < right.priority) ? rotateRight() : rotateLeft();
            else if (left == null)
                return rotateLeft();
            else
                return rotateRight();
        }

        /**
         * Returns the string representation of a node.
         */
        public String toString() {
            return "(" + data.toString() + "," + priority + ")";
        }

    }

    private static final int BOUND = 100;

    private Node<E> root;
    private Random priorityGenerator;

    private Set<Integer> priorities;

    /**
     * Creates an empty Treap.
     */
    public Treap() {
        root = null;
        priorityGenerator = new Random();
        priorities = new HashSet<Integer>();
    }
    
    /**
     * Creates an empty Treap with a given seed.
     * @param seed
     */
    public Treap(long seed) {
        root = null;
        priorityGenerator = new Random(seed);
        priorities = new HashSet<Integer>();
    }

    /**
     * Adds a given key to the Treap with a random priority.
     * @param key
     * @return true if the key was successfully added to the Treap, false if the key already exists.
     */
    public boolean add(E key) {
        if (priorities.size() == BOUND)
            throw new IllegalStateException();

        int priority = priorityGenerator.nextInt(BOUND);
        while(priorities.contains(priority)) 
            priority = priorityGenerator.nextInt(BOUND);

        return add(key, priority);
    }

    /**
     * Adds a given key to the Treap with a given priority.
     * @param key
     * @param priority
     * @throws IllegalStateException if the priority is already taken.
     * @return true if the key was successfully added to the Treap, false if the key already exists.
     */
    public boolean add(E key, int priority) {
        if (priorities.contains(priority))
            throw new IllegalArgumentException();

        if (root == null) {
            root = new Node<E>(key, priority);
            return true;
        }
        
        ArrayDeque<Node<E>> path = new ArrayDeque<>();
        Node<E> current = root;

        while (current != null) {
            path.push(current);
            int i = current.data.compareTo(key);
            if (i < 0) // go right
                current = current.right;
            else if (i > 0) // go left
                current = current.left;
            else 
                return false;
        }

        Node<E> leaf = new Node<E>(key, priority);
        priorities.add(priority);
        
        Node<E> last = path.peek();
        if (last.data.compareTo(key) < 0)
            last.right = leaf;
        else
            last.left = leaf;

        reheap(path, leaf);
        return true;
    }
    
    /**
     * Restores the heap invariant given a path from the root to the current node.
     * @param path
     */
    private void reheap(ArrayDeque<Node<E>> path, Node<E> child) {
        Node<E> parent = path.poll();
        while (parent != null && parent.priority < child.priority) { // max heap
            Node<E> grandparent = path.poll();
            Node<E> nextChild;
            if (parent.left != null && parent.left.equals(child)) {
                nextChild = parent.rotateRight();
                swap(grandparent, parent, nextChild); // rotate right
            } else {
                nextChild = parent.rotateLeft();
                swap(grandparent, parent, nextChild); // rotate left
            }
            child = nextChild;
            parent = grandparent;
        }
    }

    /**
     * Performs a swap given a the parent, the current child, and next child to replace the current.
     * @param grandparent 
     * @param parent 
     * @param child 
     */
    private void swap(Node<E> grandparent, Node<E> parent, Node<E> child) {
        if (grandparent == null) {
            root = child;
        } else {
            if (grandparent.left != null && grandparent.left.equals(parent)) 
                grandparent.left = child;
            if (grandparent.right != null && grandparent.right.equals(parent))
                grandparent.right = child;
        }
    }
    
    /**
     * Deletes a given key from the treap
     * @param key
     * @return true if the key was deleted or false if the key does not exist.
     */
    public boolean delete(E key) {
        Node<E> prev = null;
        Node<E> current = root;
        // get the node 
        int i;
        while (current != null && (i = current.data.compareTo(key)) != 0) {
            if (i < 0) { // go right
                prev = current;
                current = current.right;
            } else if (i > 0) { // go left
                prev = current;
                current = current.left;
            }
        }

        if (current == null) // key did not exist
            return false;

        // rotate the node down
        while (current.left != null || current.right != null) {
            if (prev == null) { // current is the root
                root = current.rotateDown();
                prev = root;
            } else if (prev.left != null && prev.left.equals(current)) {
                // current is the previous node's left child
                prev.left = current.rotateDown();
                prev = prev.left;
            } else if (prev.right != null && prev.right.equals(current)) {
                // current is the previous node's right child
                prev.right = current.rotateDown();
                prev = prev.right;
            }
        }

        // delete the node
        if (prev == null){
            priorities.remove(root.priority);
            root = null; // remove the root of an empty tree
        } else {
            priorities.remove(current.priority);
            if (prev.left != null && prev.left.equals(current))
                prev.left = null;
            else if (prev.right != null && prev.right.equals(current))
                prev.right = null;   
        }
        return true;
    }
    
    /**
     * The internal recursive binary search for a given key and a current node.
     * @param current
     * @param key
     * @return
     */
    private boolean find(Node<E> current, E key) {
        if (current == null)
            return false;
        int i = current.data.compareTo(key);
        return (i < 0) ? find(current.right, key) : (i > 0) ? find(current.left, key) : true;
    }
    
    /**
     * Performs a binary search for a given key.
     * @param key
     * @return true is the key exists, false if it does not.
     */
    public boolean find(E key) {
        return find(root, key);
    }

    /**
     * @return the size of the current treap.
     */
    public int size() {
        return priorities.size();
    }
    
    /**
     * @return true if the treap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }
    
    /**
     * @return true if the treap is full, false otherwise.
     */
    public boolean isFull() {
        return priorities.size() == BOUND;
    }

    /**
     * The internal recursive toString method that creates a string 
     * representation via the pre-order traversal of a treap.
     * @param current
     * @param n
     * @return
     */
    private StringBuilder toString(Node<E> current, int n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++)
            b.append("-");
        if (current == null)
            return b.append("*\n");
        b.append(current.toString() + "\n");
        b.append(toString(current.left, n + 1));
        b.append(toString(current.right, n + 1));
        return b;
    }

    /**
     * Returns a string representation of the pre-order traversal of a treap.
     */
    public String toString() {
        return toString(root, 0).toString();
    }
    
}