package cs2321;

import java.util.*;
public class QuickSort<E extends Comparable<E>> implements Sorter<E> {
	
	/**
	 * sort - Perform quick sort. - Feel free to create helper methods. 
	 * @param array - Array to sort 
	 * 			E is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	 */	
	//helper method partition, splits the array into two seperate arrays, based
	//off parameter pivot.
	private int partition(E[] array, int LargeLeft, int SmallRight, E pivot) {
		//the method will return when the two indexes are equal or if smallRight becomes smaller
		while (LargeLeft <= SmallRight) {
			//since we are finding a pair of elements in the wrong spot, a while
			//loop is easier.  It will look for an element bigger than a pivot, and
			//keep the index of that element
			while (LargeLeft <= SmallRight && array[LargeLeft].compareTo(pivot) < 0) { 
				LargeLeft++; 
		}
			//after we found one index in the wrong spot, we look for an index
			//smaller than the pivot.
			while (LargeLeft <= SmallRight && array[SmallRight].compareTo(pivot) > 0) { 
				SmallRight--; 
		}
			//once both of these values are found, there locations swap and the indexes decrease
			//to either continue finding more pairs, or end the while loop
			if (LargeLeft <= SmallRight) {
				swap(array, LargeLeft, SmallRight);
				LargeLeft++;
				SmallRight--;
			}
	}
		//the method returns the next pivot, or where the array was split between
		//bigger than and larger than its previous pivot.
		return LargeLeft;
		}
	//helper method swap.  This swaps two elements in an array given their index.
	public void swap(E[] arr, int x, int y) {
		E e = arr[x];
		E f = arr[y];
		arr[x] = f;
		arr[y] = e;
	}
	//This sort could not have been done with just one parameter, so one
	//was created that can recursively call its partitions, after the original array
	//was split.
	public void sort(E[] array, int min, int max) {
		//the base case is if min is greater than or equal to max, because there is no array
		//to sort anymore.
		if (min >= max) {
			return;
		}
		//store the pivot as the first element in the array, and swap it with the last
		//index in the array.  I kind of just made this random and it worked the best
		//with the minimum element.
		E p = array[min];
		swap(array, min, max);
		//the partition method, as previously explained, returns the next pivot, so 
		//store this as a variable.
		int mid = partition(array, min, max - 1, p);
		//to keep the array sorted, swap the mid index and the max index.
		//this now means that the mid index is in the correct spot, and we need to
		//sort from everywhere else.
		swap(array, mid, max);
		//recursively sort these arrays until an element is placed in the correct
		//spot over and over again.  Every call is another element
		sort(array, min, mid - 1);
		//do the same with the other half, from mid + 1 to max.
		sort(array, mid + 1, max);
		
	}
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" O(n^2) ")
	//TODO: replace XXX with the expected  running time complexity for the method
	@TimeComplexityExpected(" O(log(n))")
	
	@Override
	public void sort(E[] array) {
		/* Quick sort runs in o(log(n)), but can be as bad as o(n^2), depending on the 
		 * array and its pivot.  Every call sorts splits the array in 2, and sorts 2 elements
		 * ultimately making it o(log(n)), but a bad pivot, in an aberrant array can take this
		 * to o(n^2).
		 */
		//the only call this method needs is the original array, from 0 to length -1.
		//the other method with parameterized lengths will recursively sort the rest.
		sort(array, 0, array.length-1);
	}
}
