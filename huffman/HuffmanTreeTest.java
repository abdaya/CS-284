package huffman;

import static org.junit.Assert.*;
import org.junit.Test;

public class HuffmanTreeTest {

    private final String s = "AAAAAABCCCCCCDDEEEEEFGGGHIIIIIIIIIIJJJJJ";
    private final HuffmanTree h;

    public HuffmanTreeTest() {
        h = new HuffmanTree(s);
    }

    @Test
    public void testBitsToString() {
        assertTrue(h.bitsToString(new Boolean[] { true, false, true }).equals("101"));
        assertTrue(h.bitsToString(new Boolean[] { false, true, false }).equals("010"));
        assertTrue(h.bitsToString(new Boolean[] { false, false, false }).equals("000"));
        assertTrue(h.bitsToString(new Boolean[] { true, true, true }).equals("111"));
    }

    @Test
    public void testEncode() {
        assertArrayEquals(new Boolean[] { true, true, true }, h.encode("A"));
        assertArrayEquals(new Boolean[] { false, true, true, false, false }, h.encode("B"));
        assertArrayEquals(new Boolean[] { true, true, false }, h.encode("C"));
        assertArrayEquals(new Boolean[] { false, true, true, true, true }, h.encode("D"));
        assertArrayEquals(new Boolean[] { false, true, false }, h.encode("E"));
        assertArrayEquals(new Boolean[] { false, true, true, true, false }, h.encode("F"));
        assertArrayEquals(new Boolean[] { false, false, false }, h.encode("G"));
        assertArrayEquals(new Boolean[] { false, true, true, false, true }, h.encode("H"));
        assertArrayEquals(new Boolean[] { true, false }, h.encode("I"));
        assertArrayEquals(new Boolean[] { false, false, true }, h.encode("J"));

        assertArrayEquals(new Boolean[] { true, true, true, false, false, true, true, true, false, false, true, true,
                true, true, false, true, true, false, true }, h.encode("AJCDH"));

        try {
            h.encode("AZ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testEncodeEfficient() {
        assertArrayEquals(new Boolean[] { true, true, true }, h.efficientEncode("A"));
        assertArrayEquals(new Boolean[] { false, true, true, false, false }, h.efficientEncode("B"));
        assertArrayEquals(new Boolean[] { true, true, false }, h.efficientEncode("C"));
        assertArrayEquals(new Boolean[] { false, true, true, true, true }, h.efficientEncode("D"));
        assertArrayEquals(new Boolean[] { false, true, false }, h.efficientEncode("E"));
        assertArrayEquals(new Boolean[] { false, true, true, true, false }, h.efficientEncode("F"));
        assertArrayEquals(new Boolean[] { false, false, false }, h.efficientEncode("G"));
        assertArrayEquals(new Boolean[] { false, true, true, false, true }, h.efficientEncode("H"));
        assertArrayEquals(new Boolean[] { true, false }, h.efficientEncode("I"));
        assertArrayEquals(new Boolean[] { false, false, true }, h.efficientEncode("J"));

        assertArrayEquals(new Boolean[] { true, true, true, false, false, true, true, true, false, false, true, true,
                true, true, false, true, true, false, true }, h.efficientEncode("AJCDH"));

        try {
            h.efficientEncode("AZ");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDecode() {
        assertTrue("A".equals(h.decode(new Boolean[] { true, true, true })));
        assertTrue("B".equals(h.decode(new Boolean[] { false, true, true, false, false })));
        assertTrue("C".equals(h.decode(new Boolean[] { true, true, false })));
        assertTrue("D".equals(h.decode(new Boolean[] { false, true, true, true, true })));
        assertTrue("E".equals(h.decode(new Boolean[] { false, true, false })));
        assertTrue("F".equals(h.decode(new Boolean[] { false, true, true, true, false })));
        assertTrue("G".equals(h.decode(new Boolean[] { false, false, false })));
        assertTrue("H".equals(h.decode(new Boolean[] { false, true, true, false, true })));
        assertTrue("I".equals(h.decode(new Boolean[] { true, false })));
        assertTrue("J".equals(h.decode(new Boolean[] { false, false, true })));

        assertTrue("AJCDH".equals(h.decode(new Boolean[] { true, true, true, false, false, true, true, true, false, false, true, true,
                true, true, false, true, true, false, true })));

        try {
            h.decode(new Boolean[]{ false, true, true, true, false, true});
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testToString() {
        String tree = "(freq= 40)\n (freq= 18)\n  (freq= 8)\n   [value= G,freq= 3]\n   [value= J,freq= 5]\n  (freq= 10)\n   [value= E,freq= 5]\n   (freq= 5)\n    (freq= 2)\n     [value= B,freq= 1]\n     [value= H,freq= 1]\n    (freq= 3)\n     [value= F,freq= 1]\n     [value= D,freq= 2]\n (freq= 22)\n  [value= I,freq= 10]\n  (freq= 12)\n   [value= C,freq= 6]\n   [value= A,freq= 6]\n";
        assertTrue(tree.equals(h.toString()));
    }

}