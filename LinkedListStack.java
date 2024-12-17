package cs2321;
/* @author Andrew Mleczek

*/
import net.datastructures.Stack;

public class LinkedListStack<E> implements Stack<E> {
	private int size = 0;  // number of actual game entries on the board
	private node<E> top=null;  // the head of the list of game entries. 
	private node<E> bottom = null;
	
	private static class node<E> {
		node<E> next;
		E data;
		public node(E data, node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public void push(E e) {
		node<E> node = new node<E>(e, null);
		if (top == null) {
			bottom = node;
			top = node;
			size++;
		}
		else {
			node.next = top;
			top = node;
			size++;
		}	
	}

	@Override
	public E top() {
		E e = top.data;
		return e;
	}

	@Override
	public E pop() {
		if (top == null) {
			return null;
		}
		else {
			E e = top.data;
			top = top.next;
			size--;
			return e;
		}
		// TODO Auto-generated method stub
	}

}
