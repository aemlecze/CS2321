package cs2321;
import cs2321util.HeapAPQ;
public class HeapPQSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform an PrioiryQueue Sort using the heap implementation of PQ.
	 * @param array - Array to sort
	 * 			K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method.
	 */
	
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity("n log(n)")
	public void sort(E[] array) {
		// TODO: write the time complexity justification below as comment
		/* This sort is nlogn.  The two operations are inserting the elements from the array into 
		 * the heap, which is nlogn, because the loop runs n times and insert runs in logn.  The while statement
		 * is also nlogn, because of the same reasons, but the are not embedded, so asymptotically it is nlogn.    
		 */


		//TODO: complete this sort algorithm. 
		//create a heap sorted priority queue
	HeapAPQ<E,E> heap = new HeapAPQ<E,E>();
	//add all the elements into the heap
		for (int i = 0; i < array.length; i++) {
			heap.insert(array[i], null);
		}
		//create i as a count for index
		int i = 0;
		//until the heap is empty, remove the min element of the heap, which is already sorted, effectively sorting the array.
		while (!(heap.isEmpty())) {
			array[i] = heap.removeMin().getKey();
			i++;
		}
	}

}
