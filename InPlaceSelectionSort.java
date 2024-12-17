package cs2321;

public class InPlaceSelectionSort<E extends Comparable<E>> implements Sorter<E> {

	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 * 			K is an generic, but comparable type. 
	 *          Don't cast K to int type . Don't use == to compare keys, use compareTo method.
	 */

	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" O(n^2) ")
	public void sort(E[] array) {
		// TODO: write the time complexity justification below as comment
		/* Selection sort runs in o(n^2).  The sort has two for loops, both limited to
		 * the array size, and one is embedded in the other.
		 * This means for every iteration it will run n times, so n*n or n^2.
		 *     
		 */

		// TODO Auto-generated method stub
		//make a for loop with index i.
		for (int i = 0; i < array.length; i++) {
			// the minimum will be index i.
			E min = array[i];
			//make a for loop with j being the next element after i.  This will run the entirety of the array.
			for (int j = i + 1; j < array.length; j++) {
				//if the current element index j is less than the set minimum index i, swap the two elements. 
				//the for loop will continue to run, eventually finding the smallest element
				//in the array, and inserting it into index i, which is the correct spot for said element.
				if (array[j].compareTo(min) < 0) {
					min = array[j];
					E temp = array[j];
					array[j] = array[i];
					array[i] = temp;
				}
			}
			
		}
		
	}

}
