package notes.trees;

import java.util.*;

import notes.lists.SLL;

public class BTree<E> {

    public static class Node<F> {
        private F data;
        private Node<F> left;
        private Node<F> right;

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

    public BTree() {
        root = null;
        size = 0;
    }

    public BTree(E item) {
        root = new Node<>(item);
        size = 1;
    }

    public BTree(E item, BTree<E> lt, BTree<E> rt) {
        root = new Node<E>(item, lt.root, rt.root);
        size = lt.size + rt.size + 1;
    }

    private int height(Node<E> current) {
        if (current == null) {
            return 0;
        } else {
            return 1 + Math.max(height(current.left), height(current.right));
        }
    }

    public int height() {
        return height(root);
    }

    private Node<E> mirror(Node<E> current) {
        if (current == null) {
            return null;
        } else {
            return new Node<>(current.data, mirror(current.right), mirror(current.left));
        }
    }

    public void mirror() {
        mirror(root);
    }

    private boolean isomorphic(Node<E> current1, Node<E> current2) {
        return (current1 == null && current2 == null) || !(current1 == null && current2 != null)
                || !(current1 != null && current2 == null)
                || (isomorphic(current1.left, current2.right) && isomorphic(current1.right, current2.left))
                || (isomorphic(current1.left, current2.left) && isomorphic(current1.right, current2.right));
    }

    public boolean isomorphic(BTree<E> t) {
        return isomorphic(root, t.root);
    }

    private SLL<E> preorder(Node<E> current) {
        SLL<E> result = new SLL<>();
        if (current == null) {
            return result;
        } else {
            result.addFirst(current.data);
            result.append(preorder(current.left));
            result.append(preorder(current.right));
        }
        return result;
    }

    public SLL<E> preorder() {
        return preorder(root);
    }

    private SLL<E> inorder(Node<E> current) {
        SLL<E> result = new SLL<>();
        if (current == null) {
            return result;
        } else {
            result.append(inorder(current.left));
            result.append(current.data);
            result.append(inorder(current.right));
        }
        return result;
    }

    public SLL<E> inorder() {
        return inorder(root);
    }

    private SLL<E> postorder(Node<E> current) {
        SLL<E> result = new SLL<>();
        if (current == null) {
            return result;
        } else {
            result.append(postorder(current.left));
            result.append(postorder(current.right));
            result.append(current.data);
        }
        return result;
    }

    public SLL<E> postorder() {
        return postorder(root);
    }

    private BTree<Integer> sumTree(Node<Integer> curr1, Node<Integer> curr2) {
        if (curr1 == null && curr2 == null) {
            return new BTree<>();
        } else {
            int sum = (curr1 == null) ? curr2.data : (curr2 == null) ? curr1.data : curr1.data + curr2.data;
            if (curr1 == null)
                return new BTree<Integer>(sum, sumTree(null, curr2.left), sumTree(null, curr2.right));
            else if (curr2 == null)
                return new BTree<Integer>(sum, sumTree(curr1.left, null), sumTree(curr1.right, null));
            else
                return new BTree<Integer>(sum, sumTree(curr1.left, curr2.left), sumTree(curr1.right, curr2.right));
        }
    }

    public BTree<Integer> sumTree(BTree<Integer> t) {
        return sumTree((Node<Integer>) root, t.root);
    }

    public ArrayList<E> bfs() {
        ArrayList<E> result = new ArrayList<>();
        Queue<Node<E>> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node<E> curr = q.poll();
            result.add(curr.data);
            if (curr.left != null)
                q.offer(curr.left);
            if (curr.right != null)
                q.offer(curr.right);
        }
        return result;
    }

    public ArrayList<ArrayList<E>> bfs2() {
        ArrayList<ArrayList<E>> result = new ArrayList<>();
        Queue<Node<E>> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            q.offer(null);
            Node<E> curr = q.poll();
            ArrayList<E> level = new ArrayList<>();
            while (curr != null) {
                level.add(curr.data);
                if (curr.left != null)
                    q.offer(curr.left);
                if (curr.right != null)
                    q.offer(curr.right);
                curr = q.poll();
            }
            result.add(level);
        }
        return result;
    }

    private boolean ancestors(E item, Node<E> curr, ArrayList<E> path) {
        if (curr == null) {
            return false;
        } else if (curr.data == item) {
            return true;
        } else {
            if (ancestors(item, curr.left, path) || ancestors(item, curr.right, path)) {
                path.add(0, curr.data);
                return true;
            } else
                return false;
        }
    }

    public ArrayList<E> ancestors(E item) {
        ArrayList<E> path = new ArrayList<>();
        if (!ancestors(item, root, path))
            throw new IllegalArgumentException("Item is not in tree: " + item);
        return path;
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

    // private boolean isComplete(Node<E> current, Queue<Node<E>> queue) {
    // if (queue.poll() == null) {
    // while (!queue.isEmpty()) {
    // if (queue.poll() != null) {
    // return false;
    // }
    // }
    // return true;
    // }
    // Node<E> current = queue.poll();
    // queue.add(current.left);
    // queue.add(current.right);
    // return isComplete(queue);
    // }

    // public boolean isComplete() {
    // Queue<Node<E>> q = new LinkedList<>();
    // q.add(root);
    // return isComplete(root, q);
    // }

    public static void main(String[] args) {

        BTree<Integer> t0 = new BTree<>(3);
        BTree<Integer> t111 = new BTree<>(24, new BTree<Integer>(14), new BTree<Integer>());
        BTree<Integer> t1 = new BTree<>(12, new BTree<>(10), t111);
        BTree<Integer> t = new BTree<>(7, t0, t1);
        System.out.println(t);
        System.out.println();

        BTree<Integer> tt0 = new BTree<>(3);
        BTree<Integer> tt111 = new BTree<>(24, new BTree<Integer>(), new BTree<Integer>(14));
        BTree<Integer> tt1 = new BTree<>(12, new BTree<>(10), tt111);
        BTree<Integer> tt = new BTree<>(7, tt1, tt0);
        // System.out.println(tt);

        // System.out.println(t.sumTree(tt));
        // System.out.println(t.ancestors(14));
        System.out.println(t.bfs());
        System.out.println(t.bfs2());
    }

}