package cs2321;

import java.util.Iterator;
import net.datastructures.Entry;
import net.datastructures.Map;
import cs2321.ArrayList;
import java.util.Comparator;
public class UnorderedMap<K,V> extends AbstractMap<K,V> {
	
	/* Use ArrayList or DoublyLinked list for the Underlying storage for the map of entries.
	 * TODO:  Uncomment one of these two lines;
	 * 
	 * private DoublyLinkedList<mapEntry<K,V>> table;
	 */
	//using arrayList
	private ArrayList<mapEntry<K,V>> table;
	//size = 0
	int size = 0;
	//constructor for unorderedMap
	public UnorderedMap() {
		//instantiate the table as an arrayList
		table = new ArrayList<>();
	}
	

	//return the size
	@Override
	public int size() {
		return table.size;
	}
	//returns if empty
	@Override
	public boolean isEmpty() {
		return table.size == 0;
	}

	private int findIndex(K key) {
	  int n = table.size( );
	    for (int j=0; j < n; j++) {
	      if (table.get(j).getKey( ).equals(key)) {
	         return j;
	      }
	    }
	    return -1;  
	}
	//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(n))")
	@Override
	public V get(K key) {
			/*TCJ
			 * for an unorderedMap, the worst and expected is o(n), since we have to 
			 * iterate every index.
			 */
			 
				   int j = findIndex(key);
				   if (j == -1) {
					   return null;                               // not found
				   }
				   return table.get(j).getValue( );
				 }
	
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(n)")
	@Override
	public V put(K key, V value) {
		/* TCJ
		 * put runs in o(n), average and worse, since the new value is added last. 
		 * 
		 */
		/* use equals method to compare keys, do NOT use == */
		// TODO Auto-generated method stub
		//add the new entry to the index of the size.
			int j = findIndex(key);
			  if (j == -1) {
			      table.addLast(new mapEntry<>(key, value));               
			      return null;
			   }  else  {
				  V v = table.get(j).getValue();
				  table.get(j).setValue(value);
			      return v;         
			   }
			  }
	
		//helper method for the hashmap
	public int hasKey(UnorderedMap<K,V> p, K key) {
		//boolean has
		boolean has = false;
		//index j
		int j = 0;
		//iterate through the table
		for (int i = 0; i < p.size(); i++) {
			//if the index key equals the designated key, has = true.
			if (table.get(i).getKey() == key) {
				has = true;
			}
			else {
				j++;
			}
		}
		//if has is true, return the index, else return -1, meaning it doesn't exist.
		return has ? j : -1;
	}
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(n)")
	@Override
	
		/* use equals method to compare keys, do NOT use == */
		// TODO Auto-generated method stub
		/* TCJ
		 * It has to find the key to remove. This has to iterate through the 
		 * list, which is o(n)
		 */
		//return nothing if there isn't a table.	
			public V remove(K key) {
				   int j = findIndex(key);
				   int n = size( );
				   if (j == -1) {
					   return null;                               
				   }
				   V answer = table.get(j).getValue( );
				   if (j != n - 1) {
				      table.set(j, table.get(n -1)); 
				   }
				   table.remove(n -1);                                      
				   return answer;
	}
		//iterator class from book
	private class EntryIterator implements Iterator<Entry<K,V>> {
		//index j
		   private int j=0;
		   //has next
		   public boolean hasNext( ) { 
			   return j < table.size( ); }
		   //returns the next entry
		    public Entry<K,V> next( ) {
		     if (j == table.size( )) {
		    	 //if j surpasses the size, throw error
		    	 throw new IllegalStateException( );
		     }
		     //return the next entry
		     return table.get(j++);
		    }
		    public void remove( ) { 
		    	throw new UnsupportedOperationException( ); }
		  }
	//make a new iterator
		  private class EntryIterable implements Iterable<Entry<K,V>> {
		   public Iterator<Entry<K,V>> iterator( ) { 
			   return new EntryIterator( ); 
			   }
		  }
		  //entry set returns the iterator from the class.
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		return new EntryIterable();
	}

}
