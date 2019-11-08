package notes.trees;

/**
 * Binary Search Tree of non-null elements
 * 
 * @param <E>
 */
public class BST<E extends Comparable<E>> {

    public static class Node<F> {
        private F data;
        private Node<F> left, right;

        public Node(F data, Node<F> left, Node<F> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node(F data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

    }

    private Node<E> root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public BST(E item) {
        root = new Node<E>(item);
        size = 1;
    }

    public BST(E item, BST<E> left, BST<E> right) {
        root = new Node<E>(item, left.root, right.root);
        size = 1 + left.size + right.size;
    }

    private boolean find(E key, Node<E> current) {
        if (current == null)
            return false;

        int i = current.data.compareTo(key);
        if (i == 0) {
            return true;
        } else {
            if (i < 0) {
                return find(key, current.right);
            } else {
                return find(key, current.left);
            }
        }
    }

    public boolean find(E key) {
        return root == null ? false : find(key, root);
    }

    private Node<E> add(E key, Node<E> current) {
        if (current == null) {
            size++;
            return new Node<E>(key);
        }

        int i = current.data.compareTo(key);
        if (i == 0) {
            throw new IllegalArgumentException();
        } else if (i < 0) {
            current.right = add(key, current.right);
        } else {
            current.left = add(key, current.left);
        }
        return current;
    }

    public void add(E item) {
        root = add(item, root);
    }

    // private Node<E> removeMax(Node<E> current) {
    // if (current.right == null) {
    // current = current.
    // }
    // return
    // }

    private Node<E> remove(Node<E> current, E key) {
        if (current == null) {
            throw new IllegalArgumentException();
        }

        int i = current.data.compareTo(key);
        if (i == 0) {
            if (current.left == null) {
                current = current.right;
            } else if (current.right == null) {
                return current.left;
            } else {
                current.data = max(current.left);
                current = remove(current.left, current.data);
                // current = removeMax(current.right);
            }
            size--;
        } else {
            if (i < 0) {
                current.right = remove(current.right, key);
            } else {
                current.left = remove(current.left, key);
            }
        }
        return current;
    }

    public void remove(E key) {
        root = remove(root, key);
    }

    private E max(Node<E> current) {
        return current.right == null ? current.data : max(current.right);
    }

    public E max() {
        if (root == null)
            throw new IllegalStateException();
        return max(root);
    }

    private StringBuilder toString(Node<E> current, int n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) {
            b.append("-");
        }
        if (current == null) {
            return b.append("null\n");
        } else {
            b.append(current.data + "\n");
            b.append(toString(current.left, n + 1));
            b.append(toString(current.right, n + 1));
        }
        return b;
    }

    public String toString() {
        return toString(root, 0).toString();
    }

    public static void main(String[] args) {
        BST<Integer> t0 = new BST<>(15);
        BST<Integer> t10 = new BST<>(74, new BST<Integer>(), new BST<>(80));
        BST<Integer> t1 = new BST<>(89, t10, new BST<>(102));
        BST<Integer> t = new BST<>(27, t0, t1);

        // System.out.println(t);

        // System.out.println(t.find(80));
        // System.out.println(t.find(1000));

        // t.add(85);
        System.out.println(t);

        t.remove(89);
        System.out.println(t);
    }
}