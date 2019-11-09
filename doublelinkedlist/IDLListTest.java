package doublelinkedlist;

import doublelinkedlist.IDLList;

import static org.junit.Assert.*;
import org.junit.Test;

public class IDLListTest {

    @Test
    public void testConstructor() {
        IDLList<Integer> dll = new IDLList<>();
        assertEquals(0, dll.size());
    }

    @Test
    public void testAdd() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.add(i);
        }

        assertEquals(size, dll.size());

        dll.add(1);
        assertEquals(new Integer(1), dll.get(0));
        assertTrue(dll.add(1));

        dll.add(2);
        assertEquals(new Integer(2), dll.get(0));
        assertTrue(dll.add(1));

        dll.add(3);
        assertEquals(new Integer(3), dll.get(0));
        assertTrue(dll.add(1));
    }

    @Test
    public void testAddAtIndex() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.add(i);
        }

        dll.add(2, 10);
        assertEquals(new Integer(10), dll.get(2));

        dll.add(0, 20);
        assertEquals(new Integer(20), dll.get(0));
        assertEquals(new Integer(20), dll.getHead());

        // Should throw Exception
        // Index is greater than size - 1
        try {
            dll.add(20, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals(1, 1);
        }
    }

    @Test
    public void testAppend() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.append(i);
        }

        assertEquals(size, dll.size());

        dll.append(100);
        size++;
        assertEquals(size, dll.size());
        assertEquals(new Integer(100), dll.getLast());

        dll.append(200);
        size++;
        assertEquals(size, dll.size());
        assertEquals(new Integer(200), dll.getLast());

        assertTrue(dll.append(10));
    }

    @Test
    public void testGetHead() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        int head = -1;
        for (int i = 0; i < size; i++) {
            dll.add(i);
            head = i;
        }

        assertEquals(new Integer(head), dll.getHead());
        dll.removeAt(0);
        assertEquals(new Integer(head - 1), dll.getHead());
    }

    @Test
    public void testGetLast() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        int last = -1;
        for (int i = 0; i < size; i++) {
            dll.append(i);
            last = i;
        }

        assertEquals(new Integer(last), dll.getLast());
        assertEquals(size, dll.size());

    }

    @Test
    public void testGet() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.append(i);
        }

        assertEquals(new Integer(0), dll.get(0));
        assertEquals(new Integer(5), dll.get(5));
        assertEquals(new Integer(9), dll.get(9));
    }

    @Test
    public void testRemove() {
        IDLList<Integer> dll = new IDLList<>();

        // Should throw an exception
        // List is empty
        try {
            dll.remove();
            fail();
        } catch (IllegalStateException e) {
            assertEquals(1, 1);
            ;
        }

        int size = new Integer(10);
        int head = new Integer(0);
        for (int i = head; i < size; i++) {
            dll.append(i);
        }

        assertEquals(new Integer(head), dll.remove());
        assertEquals(size - 1, dll.size());
    }

    @Test
    public void testRemoveLast() {
        IDLList<Integer> dll = new IDLList<>();

        // Should throw an exception
        // List is empty
        try {
            dll.removeLast();
            fail();
        } catch (IllegalStateException e) {
            assertEquals(1, 1);
        }

        int size = new Integer(10);
        int last = -1;
        for (int i = 0; i < size; i++) {
            dll.append(i);
            last = i;
        }

        assertEquals(new Integer(last), dll.removeLast());
        assertEquals(size - 1, dll.size());
    }

    @Test
    public void testRemoveAt() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.append(i);
        }

        // Should throw an exception
        // Index is out of bounds
        try {
            dll.removeAt(100);
            fail();
        } catch (IllegalStateException e) {
            assertEquals(1, 1);
        }

        assertEquals(new Integer(size - 1), dll.removeAt(size - 1));
        size--;
        assertEquals(size, dll.size());

        assertEquals(new Integer(5), dll.removeAt(5));
        size--;
        assertEquals(size, dll.size());

        assertEquals(new Integer(0), dll.removeAt(0));
        size--;
        assertEquals(size, dll.size());

    }

    @Test
    public void testRemoveFirstOccurance() {
        IDLList<Integer> dll = new IDLList<>();
        int size = new Integer(10);
        for (int i = 0; i < size; i++) {
            dll.add(i);
        }

        assertFalse(dll.remove(100));
        assertEquals(size, dll.size());

        assertTrue(dll.remove(0));
        size--;
        assertEquals(size, dll.size());
        assertNotEquals(dll.getLast(), new Integer(0));
    }

    @Test
    public void testToString() {
        IDLList<Integer> dll = new IDLList<>();

        for (int i = 0; i < 10; i++) {
            dll.add(i);
        }

        assertEquals("[ 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, ]", dll.toString());
    }

}