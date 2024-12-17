package cs2321;

import java.util.Comparator;
import net.datastructures.*;
import cs2321util.ArrayList;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author
 */

public class HeapAPQ<K,V> implements AdaptablePriorityQueue<K,V> {
	int dCapacity = 15;	
	Comparator<K> c;
	ArrayList<apqEntry<K,V>> aList;
	public static class DefaultComparator<K> implements Comparator<K> {
		
		// This compare method simply calls the compareTo method of the argument. 
		// If the argument is not a Comparable object, and therefore there is no compareTo method,
		// it will throw ClassCastException. 
		public int compare(K a, K b) throws IllegalArgumentException {
			if (a instanceof Comparable ) {
			   return ((Comparable <K>) a).compareTo(b);
			} else {
				throw new  IllegalArgumentException();
			}
		}
	}
	//creates an entry, implementing Entry<K,V>.
	private static class apqEntry<K,V> implements Entry<K,V> {
		//class variables that we need to keep track of, including array index.
		K key;
		V value;
		int index;
		//every entry has a key, value, and index
		public apqEntry(K key, V value, int index) {
			this.key = key;
			this.value = value;
			this.index = index;
		}
		@Override
		//returns the key
		public K getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		//returns value
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}
		//sets the key with parameter
		public void setKey(K k) {
			k = key;
		}

		//sets the value with parameter.
		public void setValue(V v) {
			v = value;
		 
		}
		
	}
		
	
	/* If no comparator is provided, use the default comparator. 
	 * See the inner class DefaultComparator above. 
	 * If no initial capacity is specified, use the default initial capacity.
	 */
	//default heap, with defualt comparator and arraylist
	public HeapAPQ() {
		c = new DefaultComparator<K>();
		aList = new ArrayList<apqEntry<K,V>>(dCapacity);
	}
	
	/* Start the PQ with specified initial capacity */
	// heap with default comparator and arrayList with parameterized capacity
	public HeapAPQ(int capacity) {
		c = new DefaultComparator<K>();
		aList = new ArrayList<apqEntry<K,V>>(capacity);
	}
	
	
	/* Use specified comparator */
	//heap with parameterized comparator, and default arrayList.
	public HeapAPQ(Comparator<K> c) {
		this.c = c;
		aList = new ArrayList<apqEntry<K,V>>(dCapacity);
	}
	
	/* Use specified comparator and the specified initial capacity */
	//heap with parameterized comparator and an arrayList with set capacity.
	public HeapAPQ(Comparator<K> c, int capacity) {
		aList = new ArrayList<apqEntry<K,V>>(capacity);
		this.c = c;
		}
	

	@Override
	//returns size.
	public int size() {
		return aList.size();
	}

	@Override
	//returns if the heap is empty
	public boolean isEmpty() {
		return size() == 0;
	}
	
	//helper methods.  All are pretty self explanatory.
	//parent finds the parents index.
	public int parent(int index) {
		return (index - 1) / 2;
	}
	//left finds the left index
	public int left(int index) {
		return (index * 2) + 1;
	}
	//right finds the right index
	public int right(int index) {
		return (index * 2) + 2;
	}
	//checks if right exists.  I tried to use != null, but that is not allowed for
	//integers.  The methods above will always return an integer, just because they are
	//simply arithmetic.  If this integer is not within the size of the array, which
	//would usually be null, then it returns false.
	boolean hasRight(int index) {
		return right(index) < size();
	}
	//check if left exists.
	boolean hasLeft(int index) {
		return left(index) < size();
	}
	//helper method swap
	public void swap (int index1, int index2) {
		//makes two entries based on the indexes specified in the parameter.
		apqEntry<K,V> one = aList.get(index1);
		apqEntry<K,V> two = aList.get(index2);
		//these two are then swapped in the array based off index, and then their
		//indexes themselves switch, to keep the array sorted, and therefore the heap.
		aList.set(index1, two);
		aList.set(index2, one);
		two.index = index1;
		one.index = index2;
	}
	//helper method 'upheap' to retain heap order.
	public void upheap(int index) {
		//upheap is much simpler than downheap.  The while statement just
		//checks if the nodes parent is bigger than itself, if it is, then they swap,
		//with help from the swap method, and repeat with the new parent. 
		//When the the parent is smaller, the heap order is retained and the loop ends.
		while (index > 0 && c.compare(aList.get(index).getKey(), aList.get(parent(index)).getKey()) < 0) {
			swap(index, parent(index));
			index = parent(index);
		}
	}
	//helper method 'downheap' to retain heap order.
	public void downheap(int index) {
		// This was the method I struggled the most with.  I did not know how to 
		//traverse both sides, but it turns out I only had to check for left.
		while (hasLeft(index) || hasRight(index)) {
			//while the position has left, the left and right nodes are determined
			//and compared.
			int i = index;
			int left = left(index);
			int right = right(index);
			//if the left is smaller, then the index changes to the left, and repeats
			//in accordance to the while loop.
			if (c.compare(aList.get(left).getKey(), aList.get(i).getKey()) < 0) {
				i = left;
			}
			//if left is bigger, then we must check the right, if it even exists.
			//the if statement checks for its existence and then compares the two.
			//if it is smaller, then the index changes to the right and repeats in 
			//accordance to the while loop
			if (hasRight(index) && (c.compare(aList.get(right).getKey(), aList.get(i).getKey())) < 0) {
				i = right;
			}
			//the last case we have to check is if they are the same.
			if (i == index) {
				break;
			}
			//if the loop didn't break, to assure the loop runs again, we have to change
			// the parameterized variable 'index'.  Swap the current position with
			//its predetermined child, and then update the variable so it runs again.
			swap(index, i);
			index = i;
		}
	}
	
	@TimeComplexity(" O(log(n)) ")
	@TimeComplexityAmortized(" O(log(n)) ")
	@Override
	//inserts an entry into the heap
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		/* insert runs in log(n) time.  This is because the only actions being done
		 * are addLast, which is constant for an arrayList, and upHeap, which
		 * is log(n).  Therefore, insert is log(n).  The amortized cost is also O(n).    
		 */
		//inserts an entry into the heap.  My original method used aList.size instead
		//of the variable i, but that was giving me errors. With the variable i, it
		//worked
		int i = aList.size();
		apqEntry<K,V> newE = new apqEntry<K,V>(key, value, i);
		aList.addLast(newE);
		upheap(i);
		return newE;
		
	}

	@TimeComplexity(" O(1) ")
	@Override
	//retrives the minimum entry in the heap
	public Entry<K, V> min() {
		/* retrieving the min is as simple as getting the root when
		 * the heap is in proper order.  This is just the first element
		 * in the arrayList, so it runs in constant time.    
		 */
		if (size() == 0 ) { 
			return null;
		}
		return aList.get(0);
		}
	public Entry<K, V> removeMax() {
		if (size() ==  1) {
			return aList.get(0);
		}
		return aList.removeLast();
	}
	@TimeComplexity(" O(log(n)) ")
	@Override
	public Entry<K, V> removeMin() {
		/* removing the min would be as simple as shifting the array one space over,
		 * making this o(n), but we need to retain heap order using downheap.
		 * all operations in this method are constant, except downheap, which is log(n),
		 * making removeMin O(log(n)) time.    
		 */
		//make two entries, one for the min, and one for the max, or the last value.
		// set the last entry index to 0, and make it also the first entry in the array, 
		//this way, when we downheap, the array has to sort through the whole thing because
		//the max has become the root.  This retains heap order, and returns the old min.
		//if heap is empty, return null
		if (size() == 0) {
			return null;
		}
		//if the size is one, we can shortcut and remove the first element.
		if (size() == 1) {
			return aList.remove(0);
		}
		apqEntry<K,V> min = (apqEntry<K,V>) min();
		apqEntry<K,V> max = aList.get(size() -1);
		aList.set(0, max);
		max.index = 0;
		aList.removeLast();
		downheap(min.index);
		return min;
	}

	@TimeComplexity(" O(log(n))")
	@Override
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		/* all operations done are constant, except downheap, which runs in log(n).
		 * Therefore, remove is O(log(n)).
		 *     
		 */
		 //cast the entry to an apqEntry, and make an entry of the last entry.
		apqEntry<K,V> r = (apqEntry<K,V>) entry;
		apqEntry<K,V> last = aList.get(size() -1);
		//swap the positions of the remove node and the last node, remove last
		//and downheap.
		last.index = r.index;
		aList.set(r.index, last);
		aList.removeLast();
		downheap(r.index);
		
	}

	@TimeComplexity(" O(log(n))")
	@Override
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		/* the only operations being done are up and down heap.  These both
		 * run in log(n).  Doing this operation twice is 2log(n), which is
		 * asymptotically approaching log(n), therefore it runs in O(log(n)).
		 */
		//cast entry to apq, and set this key to the parameterized key, effectively
		//replacing it.
		apqEntry<K,V> r = (apqEntry<K,V>) entry;
		r.setKey(key);
		//to keep heap order, we have to downheap and upheap, because we do not know
		//where the node is located.
		downheap(r.index);
		upheap(r.index);
	}

	@TimeComplexity(" O(1)")
	@Override
	//replaces value
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		/* this runs in constant time.  There is only one operation that can be 
		 * accessed directly.    
		 */
		//cast entry	
		apqEntry<K,V> en = (apqEntry<K,V>) entry;
		//set the value to the parameterized value
		en.value = value;
	}
	
	


}
