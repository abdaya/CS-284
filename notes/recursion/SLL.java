package notes.recursion;

public class SLL<E> {

    private static class Node<F> {
        private F data;
        private Node<F> next;

        public Node(F data, Node<F> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;

    public void addFirst(E item) {
        head = new Node<>(item, head);
        size++;
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

    private Node<E> radd(E item, int n, Node<E> current) {
        if (n == 0) {
            return current.next = new Node<>(item, current.next);
        } else {
            current.next = radd(item, n - 1, current.next);
            return current;
        }
    }

    public void radd(E item, int n) {
        if (n < 0 || n >= size) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            addFirst(item);
        } else {
            radd(item, n, head);
        }
    }

    private Node<E> rremove(int n, Node<E> current) {
        if (n == 0) {
            return current.next;
        } else {
            current.next = rremove(n - 1, current.next);
            return current;
        }
    }

    public void rremove(int n) {
        if (n < 0 || n >= size) {
            throw new IllegalArgumentException();
        }

        if (n == 0) {
            head = head.next;
            size--;
        } else {
            head = rremove(n, head);
            size--;
        }

    }

    private String toString(Node<E> current) {
        return (current == null) ? "" : current.data.toString() + ", " + toString(current.next);
    }

    public String toString() {
        return "[ " + toString(head) + " ]";
    }

    public static void main(String[] args) {
        SLL<Integer> lst = new SLL<>();
        for (int i = 0; i < 10; i++) {
            lst.addFirst(i);
        }
        lst.reverse();
        System.out.println(lst);
        lst.radd(20, 3);
        System.out.println(lst);
        lst.rremove(3);
        System.out.println(lst);
    }

}