package treaps;

public class Treap<E extends Comparable<E>> {

    private static class Node<F> {

        private int priority;
        private F key;
        private Node<F> left;
        private Node<F> right;

        public Node(F key, int priority) {
            this.key = key;
            this.priority = priority;
            this.left = null;
            this.right = null;
        }

        public Node(F key, int priority, Node<F> left, Node<F> right) {
            this.key = key;
            this.priority = priority;
            this.left = left;
            this.right = right;
        }

    }


    
}