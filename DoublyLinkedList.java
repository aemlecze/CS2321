package cs2321;
import java.util.Iterator;

import cs2321.DoublyLinkedList.Node;
import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList<E> implements PositionalList<E> {
	//create a head and tail node, with a size integer.
	Node<E> head = null;
	Node<E> tail = null;
	int size;
	/* 
	 * Node class which should contain element and pointers to previous and next nodes
	 */
	public static class Node<E> implements Position<E> {
		//TODO: implement Node class with more fields, methods, etc.
		//create the next and previous, and any generic thing for data
		E thing;
		Node<E> next;
		Node<E> prev;
		//I was having a lot of trouble with positional errors, so I got some help
		//from my sister and she said to use an owner, which can easily
		//check if the node owns the position.
		DoublyLinkedList<E> owner;
		//every node, has data, next, previous, and if they own a position.
		Node(E e, Node<E> previous, Node<E> theNext, DoublyLinkedList<E> owner) {
			thing = e;
			next = theNext;
			prev = previous;
			this.owner = owner;
			}
		
		
		@Override
		public E getElement(){
			return thing;
		}
	}
	//create a list with all default values.  The head points to the tail, and vice versa.
	public DoublyLinkedList() {
		head = new Node<E>((E)("HEAD"), null, null, this);
		tail = new Node<E>((E)("TAIL"), head, null, this);
		size = 0;
		head.next = tail;
	}
	//this is used to convert positions to nodes, if the position is viable.
	//checks for null positions, and if the parameter is a position.
	private Node<E> convert(Position<E> position) throws IllegalArgumentException {
	    try {
	      Node<E> node = (Node<E>) position;
	      if (node.owner != this) {
	        throw new IllegalArgumentException();
	      }
	      return node;
	    } catch (NullPointerException | ClassCastException e) {
	      throw new IllegalArgumentException();
	    }
	  }
	//returns the size
	@Override
	public int size() {
		return size;
	}
	//returns if size is 0
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}
	@Override
	//returns the next element, other than head, since head and tail are protected.
	public Position<E> first() {
		if(isEmpty()) {
			System.out.println("EMPTY - returning null");
			return null;
		}
		return head.next;
	}
	//returns the previous element, other than tail, since head and tail are protected.
	@Override
	public Position<E> last() {
		if(isEmpty()) {
			System.out.println("EMPTY - returning null");
			return null;
		}
		return tail.prev;
	}
	//returns the position just before the parameters.
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		return convert(p).prev;
	}
	//returns the position just after the parameters.
	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		return convert(p).next;
	}

	@Override
	//I made a case for the size being 0, to avoid errors.  Otherwise, a node is 
	//created that points its prev to head, and makes a temp node for the
	//node that is actually there.  You swap their pointers, and newFront
	// is established in the front.
	public Position<E> addFirst(E e) {
		if (size == 0) {
			Node<E> newNode = new Node<E>(e, head, tail, this);
			head.next = newNode;
			tail.prev = newNode;
			size++;
			return newNode;
		}
		Node<E> newFront = new Node<E>(e, head, null, this);
		Node<E> currentFront = head.next;
		head.next = newFront;
		newFront.next = currentFront;
		currentFront.prev = newFront;
		size++;
		return newFront;
	}

	@Override
	//Very similar to the first one. Their pointers swap and newBack is now tail.prev
	public Position<E> addLast(E e) {
		if (size == 0) {
			Node<E> newNode = new Node<E>(e, head, tail, this);
			head.next = newNode;
			tail.prev = newNode;
			size++;
			return newNode;
		}
		Node<E> newBack = new Node<E>(e, null,tail, this);
		Node<E> currentBack = tail.prev;
		tail.prev = newBack;
		newBack.prev = currentBack;
		currentBack.next = newBack;
		size++;
		return newBack;
	}
	//This changes the position to a node, and uses this a pivot for another two
	//nodes that will create pointers around placement.  When the method
	// is finished, newBefore points both ways to the position, and the ownership
	//is maintained.
	@Override
	public Position<E> addBefore(Position<E> p, E e)
			throws IllegalArgumentException {
		
		Node<E> placement = convert(p);
	    Node<E> beforePlace = placement.prev;
	    Node<E> newBefore = new Node<E>(e,null, null, this);

	    newBefore.prev = beforePlace;
	    beforePlace.next = newBefore;
	    newBefore.next = placement;
	    placement.prev = newBefore;

	    size++;
	    return newBefore;
	}
	//the same function of the last method, I practically copied and pasted it.
	//the pointers point to newAfter so its is properly added.
	@Override
	public Position<E> addAfter(Position<E> p, E e)
			throws IllegalArgumentException {
		Node<E> placement = convert(p);
	    Node<E> afterPlace = placement.next;
	    Node<E> newAfter = new Node<E>(e,null, null, this);

	    newAfter.prev = placement;
	    placement.next = newAfter;
	    newAfter.next = afterPlace;
	    afterPlace.prev = newAfter;

	    size++;
	    return newAfter;
	}
	//sets data to a position.  I made a node to save the position, then made a new
	//node that directly points to old, then old points back to it before it loses
	//ownership of its position.  the old data is returned.
	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> old = convert(p);
		E ep = old.getElement();
		Node<E> newNode = new Node<E>(e, old.prev, old.next, this);
		old.next = newNode;
		old.prev = newNode;
		old.owner = null;
		return ep;
	}
	//removes a position.  First I converted the position to a node, then made 
	//new nodes that pointed different ways.  I connected these nodes, leaving the old
	//node behind, effectively erasing it from the list.  It also
	//loses ownership.
	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		E e = p.getElement();
		Node<E> remove = convert(p);
		Node<E> before = new Node<E>(null, null, remove, this);
		Node<E> after = new Node<E>(null, remove, null, this);
		
		before.next = after;
		after.prev = before;
		size--;
		
		remove.owner = null;
        return e;
	}
	//removes the first element.  Same premise of the first method, but the new nodes
	//can have pointers to the head.  It points around the original first node
	//and removes it from the list.
	public E removeFirst() throws IllegalArgumentException {
		Node<E> current = new Node<E>(first().getElement(), head, null, this);
		Node<E> newFront = new Node<E>(head.next.getElement(), null, null, this);
		E e = newFront.getElement();
		head.next = newFront;
		newFront.prev = head;
		size--;
		if (size == 1) {
			newFront.next = tail;
			newFront.prev = head;
			head.next = newFront;
			tail.prev = newFront;
		}
		current.next = null;
		current.prev = null;
		current.thing = null;
		current.owner = null;
		return e;
	}
	//the same thing as removeFirst(), but the new nodes point to the tail, and then
	//works around last(), as a node.
	public E removeLast() throws IllegalArgumentException {
		Node<E> remove = new Node<E>(last().getElement(), null, tail, this);
		Node<E> newLast = new Node<E>(tail.prev.getElement(), remove.prev, tail, this);
		tail.prev = newLast;
		E e = newLast.getElement();
		remove.owner = null;
		return e;
	}
	


	/*
	 * Returns an array containing all of the elements in this list 
	 * in proper sequence (from first to last element).
	 * The returned array will be "safe" in that no references to it are maintained by this list. 
	 * (In other words, this method must allocate a new array). 
	 * The caller is thus free to modify the returned array.
	*/
	// creates a generic array with casting, and fills it in with the data
	//from the nodes.
	public Object [] toArray() {
		E[] strings; 
		strings = (E[]) new Object[size + 1];
		int i = 0;
	    for (Node<E> n = head.next; n != tail; n = n.next) {
	      strings[i] = n.thing;
	      i++;
	    }
	    return strings;
	}
	

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	/*
	 *Element iterator will return one element at a time  
	 */
	private class ElementIterator implements Iterator<E> {
		
		DoublyLinkedList<E> D = new DoublyLinkedList<E>();
		Iterator<E> it = D.iterator();
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return it.hasNext();
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return it.next();
		}

	}
	


	
	/************************************************************************
	 * 
	 * The method positions(), PositionInterable class and PositionIterator class
	 * have been fully implemented.  
	 * It depends the methods first(), after() that you are implementing. 
	 *
	 ************************************************************************/

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	
	/*
	 * Position iterator will return one Position at a time  
	 */
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
		
	}
	
	private class PositionIterator implements Iterator<Position<E>> {
		Position<E> p=first();
		
		@Override
		public boolean hasNext() {
			return p!=null;
		}

		@Override
		public Position<E> next() {
			Position<E> cur = p;
			p = after(p);
			return cur;
		}
		
	}

}
