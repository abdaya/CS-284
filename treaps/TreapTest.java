package treaps;

import treaps.Treap;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;

import org.junit.Test;

/**
 * @author Robert Schaedler III
 */
public class TreapTest {

    private final char[] keys;

    private long seed = 177833990L;

    public TreapTest() {
        keys = new char[] { 'z', 's', 'r', 'h', 'k', 'n', 'x', 'j', 'e', 'u', 'f', 'c', 'm', 'd', 'y', 'o', 'q', 'b' };
    }

    @Test
    public void testAdd() {
        Treap<Character> t = buildTreap();

        for (int i = keys.length - 1; i >= 0; i--) {
            assertTrue(t.find(keys[i]));
            assertFalse(t.add(keys[i]));
        }

        assertFalse(t.find('a'));
        assertFalse(t.find('g'));
        assertFalse(t.find('i'));
    }

    @Test
    public void testDelete() {
        Treap<Character> t = buildTreap();

        char[] roots = { 'o', 'h', 'f', 'e', 'b', 'd', 'c', 'n', 'k', 'j', 'm', 'q', 's', 'r', 'y', 'u', 'x', 'z' };

        // repeatedly remove root
        for (char root : roots) {
            assertTrue("Error deleting: " + root, t.delete(root));
            assertFalse("Root not deleted: " + root, t.find(root));
        }
    }
    
    @Test
    public void testPriorities() {

        Treap<Character> t = new Treap<>();

        Random priorities = new Random(seed);      
        HashSet<Integer> used = new HashSet<>();

        for (int i = 0; i < keys.length; i++) {

            int priority = priorities.nextInt(20);
            while (used.contains(priority)) 
                priority = priorities.nextInt(20);
            
            used.add(priority);
            assertTrue(t.add(keys[i], priority));

            boolean failed = false;
            boolean errorThrown = false;
            try {
                failed = !t.add('a', priority);
            } catch (Exception err) {
                errorThrown = true;
            }
            assertTrue("Priorities are not unique.", failed || errorThrown);
            assertFalse("Key was added with invalid priority.", t.find('a'));
        }
        
        assertFalse("Key was added with invalid priority.",t.find('a'));
        assertTrue("Key was added with invalid priority.",t.add('a', priorities.nextInt(100)));
    }   

    private Treap<Character> buildTreap() {

        Treap<Character> t = new Treap<>(seed);

        for (int i = 0; i < keys.length; i++) {
            assertTrue(t.add(keys[i]));
            assertTrue(t.find(keys[i]));
        }

        // 'a' should have never been added sucessfully
        assertFalse(t.find('a'));

        return t;
    }

}