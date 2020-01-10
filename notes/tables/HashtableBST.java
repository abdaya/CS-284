package notes.tables;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class HashtableBST<K extends Comparable<K>, V> {

    private ArrayList<Node<K, V>> table;

    private static final int START_CAPACITY = 101;

    private double LOAD_THRESHOLD = 0.74;
    private int numKeys;
    private int numDeletes;

    public static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }
    }

    HashtableBST() {
        table = new ArrayList<>(START_CAPACITY);
    }

    private int find(K key) {
        int index = key.hashCode() % table.size();
        return (index < 0) ? index += table.size() : index;
    }

    public V get(K key) {
        int index = find(key);
        Node<K, V> curr = table.get(index);

        int i;
        while (curr != null && (i = curr.key.compareTo(key)) != 0)
            curr = (i < 0) ? curr.right : curr.left;

        return (curr == null) ? null : curr.value;
    }

    private Node<K, V> chain(Node<K, V> curr, K key, V value) {
        if (curr == null)
            return new Node<>(key, value);

        int i = curr.key.compareTo(key);
        if (i == 0) {
            curr.value = value;
            return curr;
        } else if (i < 0) {
            curr.right = chain(curr.right, key, value);
        } else {
            curr.left = chain(curr.left, key, value);
        }
        return curr;
    }

    public V put(K key, V value) {
        int index = find(key);
        if (table.get(index) == null) { // new key
            numKeys++;
            if ((numKeys + numDeletes) / table.size() > LOAD_THRESHOLD) {
                rehash();
                index = find(key);
            }
            table.add(index, new Node<>(key, value));
        } else {
            Node<K, V> root = table.get(index);
            table.add(index, chain(root, key, value));
        }
        return value;
    }

    private void rehash() {
        ArrayList<Node<K, V>> copy = table;
        table = new ArrayList<>((table.size() * 2) + 1);
        numKeys = 0;
        numDeletes = 0;
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i) != null) {
                Queue<Node<K, V>> q = new LinkedList<>();
                q.offer(copy.get(i));
                while (!q.isEmpty()) {
                    Node<K, V> curr = q.poll();
                    if (curr.left != null)
                        q.offer(curr.left);
                    if (curr.right != null)
                        q.offer(curr.right);
                    put(curr.key, curr.value);
                }
            }
        }
    }

    /* NO REMOVE ACTIONS PROVIDED */

}