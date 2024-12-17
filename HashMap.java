package cs2321;
import java.util.Iterator;
import cs2321.ArrayList;
import net.datastructures.*;

public class HashMap<K, V> extends AbstractMap<K,V> {
	
	/* Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries.
	 * 
	 */
	private UnorderedMap<K,V>[]  table;
	int 	size;  // number of mappings(entries) 
	int 	capacity; // The size of the hash table. 
	int     DefaultCapacity = 17; //The default hash table size
	
	/* Maintain the load factor <= 0.75.
	 * If the load factor is greater than 0.75, 
	 * then double the table, rehash the entries, and put then into new places. 
	 */
	double  loadfactor= 0.75; 
	/**
	 * Constructor that takes a hash size
	 * @param hashtable size: the number of buckets to initialize 
	 */
	//constructor with one parameter
	public HashMap(int hashtablesize) {
		//Instantiate the table, make the size 0, and the capacity of the paramaterized size.
		table = (UnorderedMap<K,V>[ ]) new UnorderedMap[hashtablesize];
		size = 0;
		capacity = hashtablesize;
	
		
	}
	
	/**
	 * Constructor that takes no argument
	 * Initialize the hash table with default hash table size: 17
	 */
	//constructor with no parameter
	public HashMap() {
		//Instantiate the table, make the size 0, and the capacity of defaultCapacity, or 17.
		table = (UnorderedMap<K,V>[]) new UnorderedMap[DefaultCapacity];
		size = 0;
		capacity = DefaultCapacity;
	}
	
	/* This method should be called by map an integer to the index range of the hash table 
	 */
	//premade hashValue method, returns hashcode of object.
	private int hashValue(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	/*
	 * The purpose of this method is for testing if the table was doubled when rehashing is needed. 
	 * Return the the size of the hash table. 
	 * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
	 */
	//returns the capacity.
	public int tableSize() {
		return capacity;
	}
	
	//returns the amount of entries.
	@Override
	public int size() {
		return size;
	}
	//returns if no entries.
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" O(n) ")
	//TODO: replace XXX with the expected  running time complexity for the method
	@TimeComplexityExpected(" O(1)")
	@Override
	public V get(K key) {
		/*TCD
		 * the point of a hashmap is quick retrieval with a key based index.
		 * Since you can search based on a key, the expected time complexity
		 * is o(1), but with a bad hash function that causes a lot of collisions
		 * it can be upwards to o(n).
		 */
		//make the index of the hashValue
		int index = hashValue(key);
		//if the index is out of bounds, or doesnt exist, return null
		if (index < 0 || index > tableSize() || table[index] == null) {
			return null;
		}
		//else return the value of the key index.
		return table[index].get(key);
	}
	//from the book, instantiates a table.  Helper method for resize.
	protected void createTable() {
		table = (UnorderedMap<K,V>[ ]) new UnorderedMap[capacity];
		}
	//from the book, helper method for put, keeps load factor down, which
	//avoids collision.
	private void resize(int newCap) {
		//temp variable to retain original size.
		int ogsize = size;
		//temp array list that can insert every value in o(n) time.
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(newCap);
		    for (Entry<K,V> e : entrySet( ))
		      buffer.addLast(e);
		    //set the new capacity for calculating load factor.
		    capacity = newCap;
		    //make a new table.
		    createTable( );    
		    //put all the entries from the array table back into the new
		    //hash map, with a new capacity.
		    for (Entry<K,V> e : buffer) {
		      put(e.getKey( ), e.getValue( ));
		    }
		    //make the size the original size, or else the put method would
		    // double it.
		    size = ogsize;
		  }
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" O(n) ")
	//TODO: replace XXX with the expected  running time complexity for the method
	@TimeComplexityExpected(" O(1)")
	@Override
	public V put(K key, V value) {
		/*TCD 
		 * All hash functions are very similar.  The worst case is o(n), if every
		 * put collides with an index, due to a bad hash function.  However, with 
		 * the hash function we have, that uses 5 binary shifts, and resizing the
		 * hashmap to keep the load factor down, the collision rate is low, so the 
		 * expected cost is o(1).
		 */
		//put method, there are three cases we have to look for. The key not existing
		//and the index is null, the key not existing and the index is taken, or 
		//the key already existing.
		//make a new entry of unordered map
		UnorderedMap<K, V> e = new UnorderedMap<K,V>();
		e.put(key, value);
		//if lamda becomes too high, we need to resize to keep the load factor down.
		if (size() / tableSize() >= loadfactor) {
			resize(capacity * 2);
		}
		//put usually increases the size, so I just made it constant.
		size++;
		//get the index of the key
		int index = hashValue(key);
		//if there is nothing at this index, insert the pair.
		if (table[index] == null) {
			table[index] = e;
			return null;
		}
		//if there is something at the index, we have two things to consider, 
		//the key being the same, or a collision.
		if (table[index] != null) {
			//retrieve the list at the index.
			UnorderedMap<K,V> l = table[index];
			//I made a helper method in unordered map that tells if it has the key.
			int j = l.hasKey(l, key);
			//if it has the key, we need to update the value.
			if (j != -1) {
				// v is the return value of the current index.
				V v = l.remove(key);
				//there are no value getters or setters, so remove the key
				l.remove(key);
				// and put the key back with the new value
				l.put(key, value);
				//since we did not add any new value, size--, which cancels out the 
				//previous size++/
				size--;
				//return the old value.
				return v;
			}
			else {
				//if the key does not exist at this index, we use seperate chaining
				//to handle collisions.  put the pair in the list at this index.
				l.put(key, value);
			}
		}
		//if there is no old value to return, return null.
		return null;
	}
	
	//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(1))")
	@Override
	public V remove(K key) {
		/*TCD
		 * Remove has the same reasoning as the last two methods.  Bad hashing results
		 * in o(n), but with a good hash function it should be o(1). 
		 */
		
		//retrieve the index of the key
		int index = hashValue(key);
		//get the list at the index.
		UnorderedMap<K,V> l = table[index];
		if (l == null) {
			return null;
		}
		//get the old value
		V v = l.get(key);
		//remove the key in the list
		l.remove(key);
		//tick the size down one
		size--;
		//return the old value
		return v;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		// Hint: call entrySet of each bucket map. 
		//from book, utilizes arrayList and unorderedMap iterator for hashMap.
		//make a new arrayList
			    ArrayList<Entry<K,V>> buffer = new ArrayList<>( );
			    //while the index is less than the capacity
			    for (int h=0; h < capacity; h++)
			    	//and the index is not null
			      if (table[h] != null)
			    	  //add each entry based off the bucket iterator.
			         for (Entry<K,V> entry : table[h].entrySet( ))
			           buffer.addLast(entry);
			    //return the arrayList.
			    return buffer;
			  }
	}
	


