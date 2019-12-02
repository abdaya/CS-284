package huffman;

import java.util.*;

/*
 * Instructions: 
 * First: Read through the assignment specification, make sure you understand what the assignment is asking for.
 * Second: There are number of "TODO" instructions within this code, make sure to complete all of them fully.
 * Third: Test you code.
 */

// Pledge: I pledge my honor that I have abided by the Stevens Honor System.
// Name: Robert Schaedler III

/**
 * HW6 CS284 Fall 2019 Implements a Huffman encoding tree. The included code has
 * been commented for the student's benefit, feel free to read through it.
 */
public class HuffmanTree {

	// ******************** Start of Stub Code ******************** //
	// ************************************************************ //
	/**
	 * Node<E> is an inner class and it is abstract. There will be two kinds of
	 * Node, one for leaves and one for internal nodes.
	 */
	abstract static class Node implements Comparable<Node> {
		/** The frequency of all the items below this node */
		protected int frequency;

		public Node(int freq) {
			this.frequency = freq;
		}

		/** Needed for the Minimum Heap used later in this stub. */
		public int compareTo(Node other) {
			return this.frequency - other.frequency;
		}
	}

	/** Leaves of a Huffman tree contain the data items */
	protected static class LeafNode extends Node {
		// Data Fields
		/** The data in the node */
		protected char data;

		/** Constructor to create a leaf node (i.e. no children) */
		public LeafNode(char data, int freq) {
			super(freq);
			this.data = data;
		}

		/** toString method */
		public String toString() {
			return "[value= " + this.data + ",freq= " + frequency + "]";
		}
	}

	/**
	 * Internal nodes contain no data, just references to left and right subtrees
	 */
	protected static class InternalNode extends Node {
		/** A reference to the left child */
		protected Node left;
		/** A reference to the right child */
		protected Node right;

		/** Constructor to create an internal node */
		public InternalNode(Node leftC, Node rightC) {
			super(leftC.frequency + rightC.frequency);
			left = leftC;
			right = rightC;
		}

		public String toString() {
			return "(freq= " + frequency + ")";
		}
	}

	// Enough space to encode all "extended ascii" values
	// This size is probably overkill (since many of the values are not "printable"
	// in the usual sense)
	private static final int codex_size = 256;

	/* Data Fields for Huffman Tree */
	private Node root;

	public HuffmanTree(String s) {
		root = buildHuffmanTree(s);
	}

	/**
	 * Returns the frequencies of all characters in s.
	 * 
	 * @param s
	 * @return
	 */
	public static int[] frequency(String s) {
		int[] freq = new int[codex_size];
		for (char c : s.toCharArray()) {
			freq[c]++;
		}
		return freq;
	}

	/**
	 * Builds the actual Huffman tree for that particular string.
	 * 
	 * @param s
	 * @return
	 */
	private static Node buildHuffmanTree(String s) {
		int[] freq = frequency(s);

		// Create a minimum heap for creating the Huffman Tree
		// Note to students: You probably won't know what this data structure
		// is yet, and that is okay.
		PriorityQueue<Node> min_heap = new PriorityQueue<Node>();

		// Go through and create all the nodes we need
		// as in, all the nodes that actually appear in our string (have a frequency
		// greater then 0)
		for (int i = 0; i < codex_size; i++) {
			if (freq[i] > 0) {
				// Add a new node (for that character) to the min_heap, notice we have to cast
				// our int i into a char.
				min_heap.add(new LeafNode((char) i, freq[i]));
			}
		}

		// Edge case (string was empty)
		if (min_heap.isEmpty()) {
			throw new NullPointerException("Cannot encode an empty String");
		}

		// Now to create the actual Huffman Tree
		// NOTE: this algorithm is a bit beyond what we cover in cs284,
		// you'll see this in depth in cs385

		// Merge smallest subtrees together
		while (min_heap.size() > 1) {
			Node left = min_heap.poll();
			Node right = min_heap.poll();
			Node merged_tree = new InternalNode(left, right);
			min_heap.add(merged_tree);
		}

		// Return our structured Huffman Tree
		return min_heap.poll();
	}

	// ******************** End of Stub Code ******************** //
	// ********************************************************** //

	/**
	 * 
	 * @param encoding
	 * @return
	 */
	public String bitsToString(Boolean[] encoding) {
		StringBuilder b = new StringBuilder();
		for (Boolean bool : encoding)
			b.append((bool) ? "1" : "0");
		return b.toString();
	}

	/**
	 * The internal method used to traverse the tree in-order.
	 * @param current
	 * @param n
	 * @return
	 */
	private StringBuilder toString(InternalNode current, int n) {
		StringBuilder b = new StringBuilder();
		StringBuilder indent = new StringBuilder();

		for (int i = 0; i < n; i++)
			indent.append(" ");

		b.append(indent.toString());
		b.append(current.toString() + "\n");

		Node left = current.left;
		Node right = current.right;

		if (left instanceof LeafNode)
			b.append(indent + " " + left.toString() + "\n");
		else
			b.append(toString((InternalNode) left, n + 1));

		if (right instanceof LeafNode)
			b.append(indent + " " + right.toString() + "\n");
		else
			b.append(toString((InternalNode) right, n + 1));

		return b;
	}

	/**
	 * Returns the string representation of the current Huffman tree
	 */
	public String toString() {
		return (root instanceof InternalNode) ? toString((InternalNode) root, 0).toString() : root.toString();
	}

	/**
	 * Decodes a huffman encoding and returns its string representation
	 * @param coding
	 * @throws IllegalArgumentException if the encoding is invalid
	 * @return
	 */
	public String decode(Boolean[] coding) {

		Node curr = root;
		StringBuilder b = new StringBuilder();

		for (Boolean code : coding) {
			if (curr instanceof InternalNode)
				if (code) // go right
					curr = ((InternalNode) curr).right;
				else // go left
					curr = ((InternalNode) curr).left;

			if (curr instanceof LeafNode) {
				b.append(((LeafNode) curr).data);
				curr = root;
			}
		}

		if (curr != root)
			throw new IllegalArgumentException(curr.toString());

		return b.toString();
	}

	/**
	 * The internal search method for seaerching for a character in the Huffman tree  
	 * @param curr the current node
	 * @param path the path to the character that is built as the character is searched for
	 * @param c the caracter to search for
	 * @return true if the character is in the tree, false otherwise
	 */
	private boolean dfs(Node curr, List<Boolean> path, Character c) {
		if (curr instanceof LeafNode) {
			return (((LeafNode) curr).data == c) ? true : false;
		} else if (dfs(((InternalNode) curr).left, path, c)) {
			path.add(0, false);
			return true;
		} else if (dfs(((InternalNode) curr).right, path, c)) {
			path.add(0, true);
			return true;
		} else {
			// path.remove(path.size() - 1);
			return false;
		}
	}

	/**
	 * Encodes a given string into a boolean array based of the current huffman tree
	 * @param inputText the string to encode
	 * @throws IllegalArgumentException if the string cannot be encoded
	 * @return the boolean array representing the encoded string
	 */
	public Boolean[] encode(String inputText) {
		List<Boolean> encoding = new ArrayList<>();

		for (Character c : inputText.toCharArray()) {
			List<Boolean> temp = new ArrayList<>();
			if (!dfs(root, temp, c))
				throw new IllegalArgumentException();
			encoding.addAll(temp);
		}
		return (Boolean[]) encoding.toArray(new Boolean[encoding.size()]);
	}

	/**
	 * Encodes a given string into a boolean array based on the current huffman tree efficiently.
	 * @param inputText the string to encode
	 * @throws IllegalArgumentException if the string cannot be encoded
	 * @return the boolean array representing the encoded string
	 */
	public Boolean[] efficientEncode(String inputText) {
		Map<Character, List<Boolean>> h = new HashMap<>();
		List<Boolean> encoding = new ArrayList<>();

		for (Character c : inputText.toCharArray()) {
			if (h.containsKey(c)) {
				encoding.addAll(h.get(c));
			} else {
				List<Boolean> path = new ArrayList<>();
				if (!dfs(root, path, c))
					throw new IllegalArgumentException();
				h.put(c, path);
				encoding.addAll(path);
			}
		}
		return (Boolean[]) encoding.toArray(new Boolean[encoding.size()]);
	}

	public static void main(String[] args) {
		// Code to see if stuff works...
		String s = "Some string you want to encode";
		HuffmanTree t = new HuffmanTree(s); // Creates specific Huffman Tree for "s"
		// Now you can use encode, decode, and toString to interact with your specific
		// Huffman Tree

		System.out.println(t);
	}
}
