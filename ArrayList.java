package cs2321;

import java.util.Iterator;
import net.datastructures.List;

public class ArrayList<E> implements List<E> {
	//creates standard class variables, with an initial cap of 16.
	int size;
	int initialCap = 16;
	int cap;
	E[] array;
	
	//TODO: add class members
	
	
	private class ArrayListIterator implements Iterator<E> {
		//TODO: add class members
		//makes an array, and an iterator for the array.
		private int j = 0;
		private boolean removable = false;
		@Override
		public boolean hasNext() {
			return j < size;
		}
		@Override
		public E next() throws IllegalStateException{
			if (j == size) throw new IllegalStateException("No next element");
			removable = true;
			return array[j++];
			}
		public void remove( ) throws IllegalStateException {
			if (!removable) throw new IllegalStateException("nothing to remove");
			     ArrayList.this.remove(j -1);   
			     j--;                          
			     removable = false; 
	}
	}
	//array list with the class variable intitialCap
	public ArrayList() {
		// Default capacity: 16
		// TODO Auto-generated constructor stub
		cap = initialCap;
		array = (E[])new Object[initialCap];
		size = 0;
	}
	//arrayList with a paramater as capacity.
	public ArrayList(int capacity) {
		// create an array with the specified capacity
		// TODO Auto-generated constructor stub
		cap = capacity;
		array = (E[])new Object[cap];
		size = 0;
	}
	//returns size
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	// Return the current capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 
	//returns capacity.  Cap was used because I was confusing myself.
	public int capacity() {
		// TODO return the current capacity 
		return cap;
	}
	
	//returns if size equals 0
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}
	//retrieves an element by parameter
	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i > cap) {
			throw new IndexOutOfBoundsException();
		}
		else {
		return array[i];
		}
	}
	//sets data by parameter
	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (i < 0 || i > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		else {
		E old = array[i];
		array[i] = e;
		size++;
		return old;
		}
	}

	@Override
	//adds an element.  If the array runs out of capacity, it will double its
	//capacity
	public void add(int i, E e) throws IndexOutOfBoundsException {
		//check i
		int num = 0111;
		System.out.println(num);
		if (i < 0 || i > size() ) {
			throw new IndexOutOfBoundsException();
		}
		if(size() == capacity() ) {
			expand();
		}
		if (i == 0) {
			addFirst(e);
		}
		else if (array[i] == null) {
			array[i] = e;
			size++;
		}
		else {
			for (int j = i; j < size(); j++) {
				if (j+1 == capacity()) {
					expand();
					continue;
				}
				array[j+1] = array[j];
			}
			array[i] = e;
			size++;
		}
	}
	//removes an element.  does not have to worry about capacity errors.
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i > size()-1) {
			throw new IndexOutOfBoundsException();
		}
		E e = array[i];
		if (i == 0) {
			removeFirst();
		}
		else {
			for (int j = i; j < size()-1; j++) {
				array[j] = array[j+1];
			}
			size--;
		}
		return e;
	}

	//shifts every element over, then makes the first element the paramaterized data.
	public void addFirst(E e)  {
		// TODO Auto-generated method stub
		for (int i = 0; i <= size; i++) {
			if ((i + 1) == capacity()) {
				expand();
			}
			array[i+1] = array[i];
		}
		array[0] = e;
		size++;
	}
	//had to be weary over capacity, but it was a simple if statement away.  the last 
	//element will become the parameterized data.
	public void addLast(E e)  {
		if (size() == capacity()) {
			expand();
		}
		array[size] = e;
		size++;
		// TODO Auto-generated method stub
	}
	//removes the first element and shifts every other element back
	public E removeFirst() throws IndexOutOfBoundsException {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		E e = array[0];
		array[0] = null;
		for (int i = 1; i < size(); i++) {
			array[i-1] = array[i];
		}
		size--;
		return e;
	}
	//removes the last element, did not have to worry about capacity, only
	//empty arrays.
	public E removeLast() throws IndexOutOfBoundsException {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		E e = array[size];
		array[size] = null;
		size--;
		return e;
	}
	//this is the most vital method to this program.  When the capacity is too big, 
	//the array must expand.  It was simpler than I thought, you can just create an
	// array, double the size of it, then fill it in with a loop.  The feature I
	//learned was you can make arrays equal to each other, like on line 197.
	public void expand() {
		E[] bigger = (E[]) new Object[array.length * 2];
		for (int i = 0; i < array.length; i++) {
			bigger[i] = array[i];
		}
		cap = cap * 2;
		array = bigger;
	}
	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}



}
