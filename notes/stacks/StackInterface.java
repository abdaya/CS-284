package notes.stacks;

public interface StackInterface<E> {

    public void push(E item);

    public E pop();

    public E peek();

    public boolean isEmpty();

    public int size();

}