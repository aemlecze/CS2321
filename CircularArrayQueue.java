/**
 * @author Andrew Mleczek
 */
package cs2321; 
import net.datastructures.Queue;

/**
 * @author ruihong-adm
 * @param <E>
 *
 */

public class CircularArrayQueue<E> implements Queue<E> {
	//create the generic array, and important helper variables.  
	//the variables are initialized to zero to avoid errors.
	E [] Q;
	int first = 0; 
	int last = 0;
	int size = 0;
	public CircularArrayQueue(int queueSize) {
		// TODO: create a new queue with the specified size
		//I learned this last semester, that java does not allow generic arrays,
		//but workarounds exist, like this one.
		Q = (E[])(new Object[queueSize]);
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		//size increases as elements are added, so this returns the amount of elements
		//currently in the queue, rather than its given length.
		//I found this was more helpful than returning the same constant.
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		//returns if the size is 0.
		return size() == 0;
	}
	//I made this because I thought I would use it, but I never did.
	public boolean isFull() {
		return size() == Q.length;
	}
	@Override
	
	public E first() {
		//if it is empty ir returns null
		if (isEmpty()) {
			return null;
		}
		//otherwise it returns the first.
		return Q[first];
	}

	@Override
	public E dequeue() throws IllegalStateException {
		//if the array is empty, it will throw an exception.
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		//saves a reference to the date saved in the first slot, before it is
		//removed
		E e = Q[first];
		Q[first] = null;
		//first becomes the next element, but there were errors with 
		//queueing and dequeuing over and over again, but modding the length
		//fixed it.
		first = (first + 1) % Q.length;
		size--;
		return e;
	}

	/* Throw the exception IllegalStateException when the queue is full */
	@Override
	public void enqueue(E e) throws IllegalStateException {
		//while the queue still has room, the element will be added to the last slot.
		//I tried to use size instead of last, so as the size increased
		//it would insert it into that slot, but it was being complicated,
		//so I made the last element and it fixed the errors.
		if (size < Q.length) {
			Q[last] = e;
			last++;
			size++;
		}
		//the instructions said to throw an error if it did not have room,
		//so I just made this if, but I am still kind of confused if I was
		//supposed to make a new array that was longer to make more room.
		//I left it here because Josephus did not require that and she just said
		//to throw an error.
		if (size > Q.length) {
			throw new IllegalStateException();
		}		
	}

	    
}
