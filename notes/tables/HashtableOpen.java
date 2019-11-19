package notes.tables;

import java.util.Arrays;
import java.util.Random;

public class HashtableOpen<K, V> {

	private Entry<K, V>[] table;

	private static final int START_CAPACITY = 101;

	private double LOAD_THRESHOLD = 0.75;
	private int numKeys;
	private int numDeletes;

	private final Entry<K, V> DELETED = new Entry<K, V>(null, null);

	public static class Entry<K, V> {
		private K key;
		private V value;

		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V val) {
			V oldVal = value;
			value = val;
			return oldVal;
		}

		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}

	HashtableOpen() {
		table = new Entry[START_CAPACITY];
	}

	/**
	 * Find key using linear probing
	 * 
	 * @param key
	 * @return index where key was located
	 */
	private int find(K key) {
		int index = key.hashCode() % table.length;
		if (index < 0) 
			index += table.length;

		// index is the index to the table
		while (table[index] != null && table[index].getKey().equals(key)) 
			index = (index + 1) % table.length;
		
		return index;
	}

	public V get(K key) {
		int i = find(key);
		return (table[i] != null) ? table[i].getValue() : null;
	}

	public V put(K key, V value) {
		int i = find(key);
		if (table[i] == null) { // new key
			numKeys++;
			if ((numKeys + numDeletes) / table.length > LOAD_THRESHOLD) {
				rehash();
				i = find(key);
			}
			table[i] = new Entry<>(key, value);
			return value;
		} 

		// key already exists
		// overwrite the old value with a new one
		V old = table[i].getValue();
		table[i].setValue(value);
		return old;
	}

	private void rehash() {
		Entry<K, V>[] copy = table;
		table = new Entry[(table.length * 2) + 1];
		numKeys = 0;
		numDeletes = 0;
		for (int i = 0; i < copy.length; i++) 
			if (copy[i] != null || !copy[i].equals(DELETED)) 
				put(copy[i].getKey(), copy[i].getValue());		
		return;
	}

	/**
	 * Removes key. Does nothing if key is not in the table
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key) {
		int i = find(key);
		V temp = (table[i] != null) ? table[i].getValue() : null;
		table[i] = (temp != null) ? DELETED : null;
		numDeletes++;
		numKeys--;
		return temp;
	}

	public String toString() {
		return Arrays.toString(table);
	}

	public static void main(String[] args) {
		HashtableOpen<Character, Integer> t = new HashtableOpen<>();

		Random r = new Random();

		for (int i = 0; i < 20; i++) {
			Character key = (char) (i + 65);
			System.out.println(key + "-->" + key.hashCode());
			t.put(key, r.nextInt(1000));
		}
		System.out.println(t);
	}

}
