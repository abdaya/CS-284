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

        priorities.add(priority);
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

        Node<E> last = path.peek();
        Node<E> leaf = new Node<E>(key, priority);
        priorities.add(priority);
        
        if (last.data.compareTo(key) < 0) 
            last.right = leaf;
        else
            last.left = leaf;
        
        path.push(leaf);
        reheap(path);

        return true;
    }
    
    /**
     * Restores the heap invariant given a path.
     * @param path
     */
    private void reheap(ArrayDeque<Node<E>> path) {
        if (path.size() <= 1)
            return;

        Node<E> child = path.poll();
        Node<E> parent = path.poll();
        while (parent != null && parent.priority < child.priority) {
            Node<E> grandparent = path.poll();
            if (parent.left.equals(child)) {
                swapRight(grandparent, parent); // rotate right
            } else {
                swapLeft(grandparent, parent); // rotate left
            }
        }
    }
    
    /**
     * Performs the right rotation of a given child node reliative to its parent.
     * @param parent
     * @param child
     */
    private void swapRight(Node<E> parent, Node<E> child) {
        if (parent == null){
            root = child.rotateRight();
            return;
        }
        
        if (parent.left.equals(child)) 
            parent.left = parent.rotateRight();
        else if (parent.right.equals(child))
            parent.right = parent.rotateRight();
        else 
            throw new IllegalStateException();
    }

    /**
     * Performs the left rotation of a given child node reliative to its parent.
     * @param parent
     * @param child
     */
    private void swapLeft(Node<E> parent, Node<E> child) {
        if (parent == null) {
            root = child.rotateLeft();
            return;
        }

        if (parent.left.equals(child))
            parent.left = parent.rotateLeft();
        else if (parent.right.equals(child))
            parent.right = parent.rotateLeft();
        else
            throw new IllegalStateException();
    }
    
    public boolean delete(E key) {
        // TODO
        return true;
    }
    
    private boolean find(Node<E> current, E key) {
        while (current != null) {
            int i = current.data.compareTo(key);
            if (i < 0) // go right
                current = current.right;
            else if (i > 0) // go left
                current = current.left;
            else
                return true;
        }
        return false;
    }
    
    public boolean find(E key) {
        return find(root, key);
    }


    private StringBuilder toString(Node<E> current, int n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++)
            b.append("-");
        if (current == null)
            return b.append("*\n");
        b.append(current.toString());
        b.append(toString(current.left, n + 1));
        b.append(toString(current.right, n + 1));
        return b;
    }

    public String toString() {
        return toString(root, 0).toString();
    }
    
}