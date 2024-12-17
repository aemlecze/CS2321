package cs2321;
import java.util.*;
public class MergeSort<E extends Comparable<E>> implements Sorter<E> {
	/**
	 * sort - Perform merge sort. -Feel free to create other methods. 
	 * @param array - Array to sort
	 * 			 K is an generic, but comparable type. 
	 * 			Don't cast K to int type . Don't use == to compare keys, use compareTo method. 
	 * 
	 * 			If you need to create an array of E,  use the following line as E is Comparable
	 * 			E[]  newarray = (E[]) Comparable[];
	 */
	
	
	//merge helper method, parameters of o, t, and newa. o and t will be combined into newa.  
	public void merge(E[] o, E[] t, E[] newa) {
		//create indexes for the three arrays.  Using three indexes avoids embedded for loops, which will keep the cost down.
		int i = 0,  j = 0, k = 0;
		//the while loop will insert the elements of o and t into newa, or new array, from smallest to biggest.
		//if the first element of o is less than t, add to the array and increase the index.  Repeat until one of the arrays
		// is empty, or all of its elements have been added.
		while (i < o.length && j < t.length) {
			if (o[i].compareTo(t[j]) <= 0) {
				newa[k] = o[i];
				i++;
			}
			else if(o[i].compareTo(t[j]) > 0) {
				newa[k] = t[j];
				j++;
			}
			//increase index of bigger array, because an element has been added no matter what.
			k++;
		}
		//if there are left over elements, we need to insert them, only one of these while statements will run.
		while (i < o.length) {
			newa[k] = o[i];
			i++;
			k++;
		}
		while ( j < t.length) {
			newa[k] = t[j];
			j++;
			k++;
		}
	}
	
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" n(log(n))")
	public void sort(E[] array) {
		// TODO: write the time complexity justification below as comment
		/* 
		 *     
		 */
		//base case for the array being one element; we have split it entirely.
		if (array.length  <  2) {
            return;
        }
		//m is to find the middle index, just for simplicity sake.
		int m = array.length / 2;
		//first array has length of m
		E[] one = (E[]) new Comparable[m];
		//to avoid even or odd differentiation, the second array will have every other remaining element.
		E[] two = (E[]) new Comparable[array.length - m];
		//fill in the first array
		for (int i = 0; i < one.length; i ++) {
			one[i] = array[i];
		}
		//fill in the second array
		for (int i = 0; i < two.length; i ++) {
			two[i] = array[i + m];
		}
		//recursively call the sort of the first array, which will only work the first half until it returns.
		sort(one);
		//once the first array is split, the second half of the original array will split, until it returns.
		sort(two);
		//once all the elements are in their own array, they will merge, which sorts them in the merge method.
		merge(one, two, array);

		
		}
	
	
}


