package notes.stacks;

public class StackSLL<E> implements StackInterface<E> {

    public static class Node<F> {
        private F data;
        private Node<F> next;

        Node(F data, Node<F> next) {
            this.data = data;
            this.next = next;
        }

        Node(F data) {
            this.data = data;
            next = null;
        }
    }

    private Node<E> top;
    private int size;

    StackSLL() {
        top = null;
        size = 0;
    }

    public void push(E item) {
        top = new Node<E>(item, top);
        size++;
    }

    public E pop() {
        if (top == null) {
            throw new IllegalStateException();
        }

        E temp = top.data;
        top = top.next;
        return temp;
    }

    public E peek() {
        if (top == null) {
            throw new IllegalStateException();
        }
        return top.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public String toString() {
        Node<E> current = top;
        StringBuilder s = new StringBuilder();
        s.append("[ ");
        while (current != null) {
            s.append(current.data + ", ");
            current = current.next;
        }
        s.append("]");
        return s.toString();
    }

    public static void main(String[] args) {
        StackSLL<Integer> s = new StackSLL<>();

        for (int i = 0; i < 10; i++) {
            s.push(i);
        }

        System.out.println(s);
        System.out.println("pop: " + s.pop());
        System.out.println(s);
        System.out.println("peek: " + s.peek());
        System.out.println(s);
    }
}