package cs2321;
import java.util.Arrays;
public class InPlaceInsertionSort<E extends Comparable<E>> implements Sorter<E> {
	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 * 	 		K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	*/
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity("O(n^2)")
	public void sort(E[] array) {
		// TODO: write the time complexity justification below as comment
		/* This has embedded for loops, which usually resembles O(n^2), but taking a
		 * deeper look, it will always run at least n times, and the worst case it will
		 * n^2, because the while loop will run n more times, for every iteration, leaving us
		 * with best case O(n), and worst case O(n^2). 
		 */


		// TODO Auto-generated method stub
		//Insertion sort.
		for (int i = 1; i < array.length; i++) {
			//I originally did not make any temporary variable for storage, but it was not working
			//with just the index, so storage was made to keep track of the current index.
			E storage = array[i];
			//J is made to keep track of the previous element.
			int j = i - 1;
			//If array[j] < array[i], nothing needs to happen, so we only need to know when
			//array[j] > array[i].
			while (j >= 0 && array[j].compareTo(storage) > 0) {
				//when we find unsorted elements, we shift every element greater than array[i] to the right
				array[j+1] = array[j];
				j--;
			}	
			//when we run out of elements, j points to the element less than array[i], so 
			//array[j+1] will be array[i].
			array[j+1] = storage;
		}
	}
}
	

