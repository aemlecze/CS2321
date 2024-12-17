
package cs2321;
import net.datastructures.BinaryTree;
import net.datastructures.List;
import net.datastructures.Position;
import cs2321.*;
/**
 * @author ruihong-adm
 *	
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E> {
	//create a node class, and a root node.
	int elements;
	Node<E> root;
	//I used a booleans to see if their were children taken or not.  They will
	//be implemented later
	boolean left = false;
	boolean right = false;
	public class Node<E> implements Position<E> {
		//i needed to keep pointers for the right, left, and parent.
		Node<E> root;
		Node<E> right;
		Node<E> left;
		Node<E> parent;
		E element;
		//create a simple node.
		Node(E data, Node<E> parent, Node<E> rightChild, Node<E> leftChild) {
			element = data;
			this.parent = parent;
			right = rightChild;
			left = leftChild;
			
		}
		//for this project I used getters and setter for everything.
		//I find it easier to understand positions with nodes this way.
		public void setParent(Node<E> parent) {
			this.parent = parent;
		}
		public Node<E> getParent(Node<E> n) {
			return n.parent;
		}
		public Node<E> getRight(Position<E> p) {
			Node<E> n = (Node<E>) p;
			return n.right;
		}
		public Node<E> getLeft(Position<E> p) {
			Node<E> n = (Node<E>) p;
			return n.left;
		}
		public void setLeft(Node<E> left) {
			this.left = left;
		}
		public void setRight(Node<E> right) {
			this.right = right;
		}	
		public void setElement(E element) {
			this.element = element;
		}
		@Override
		public E getElement() {
			return element;
		}
	}
	//when the tree is made, a root is made with everything null, and there are no elements.
	public LinkedBinaryTree( ) {
		root = new Node<E>(null, null, null, null);
		elements = 0;
	}
	//this is the vital method for the program.
	private Node<E> check(Position<E> p) throws IllegalStateException{
		//it will always create a node first, and set the children to false.
	Node<E> n = (Node<E>) p;
		left = false;
		right = false;
		//if there is no node, then the position is invalid
		if (n == null) {
			throw new IllegalStateException("Position Invalid");
		}
		//if a left child exits, then left will be true, meaning there is a child
		if (n.getLeft(p) == null) {
			left = true;
		}
		//same with the right child.
		if(n.getRight(p) == null) {
			right = true;
		}
		//this next part was to help with the parent() method.  If it has two
		//children, and the parent is null, then this node must be the root.
		else if ((right == false) && (left == false)) {
			if (n.getParent(n) == null) {
				n = root;
			}
		}
		return n;
	}
	//returns the root, initialized when the user makes the tree
	@Override
	public Position<E> root() {
		return root;
	}
	//I am not sure if I ever used this, but this checks if it is the root.
	public boolean isRoot(Position<E> p) {
		Node<E> n = check(p);
		return (n == root());
	}
	//I am also not sure I ever used this either, but this gets the sibling, simply
	//by getting the parent, then returning the other node.
	public Position<E> getSibling(Position<E> p) {
		Node<E> n = check(p);
		Node<E> nparent = n.getParent(n);
		if (nparent.getLeft(nparent) == n) {
			return n.getRight(nparent);
		}
		else {
			return n.getLeft(nparent);
		}
	}
	//this returns the parent, unless it is the root, where it returns null.
	@Override
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> n = check(p);
		if (isRoot(n)) {
			return null;
		}
		else {
			return n.getParent(n);
		}
	}
	//returns the left position
	@Override	
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> n = (Node<E>) p;	
		return n.getLeft(p);
	}
	//returns the right position
	@Override
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> n = (Node<E>) p;
		return n.getRight(p);
	}
	//returns if it has at least one child, using the booleans earlier.  
	//It also checks the position, so the booleans are updated.
	@Override
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		Node<E> n = check(p);
		return ((left == false) || (right == false));
	}
	//checks if both children are taken, also checking the position to update
	//the booleans
	@Override
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		Node<E> n = check(p);
		return (left == true && right == true);
	}

	//returns the size, known as elements.
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return elements;
	}
	//returns if there are no elements.
	@Override
	public boolean isEmpty() {
		return elements == 0;
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	//since the root was already 'made', this just sets the element.  If there isn't
	// an element, then there is no root.
	public Position<E> addRoot(E e) throws IllegalStateException {
		//checks if there is already a root
		if (root.getElement() != null) {
			throw new IllegalStateException("Root Already Exists");
		}
		//makes root a node, with no parent, and null children, that can be accessed
		//with the set helper methods; size increased by one.
		root.setElement(e);
		elements++;
		return root;
	}
	
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	//this sets the node to the left of the position
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		//I start by checking the position, and updating the booleans.
		Node<E> tParent = check(p);
		//if there is already a left, you cannot add another one, so an error is thrown.
		if (left == false) {
			throw new IllegalArgumentException("Left Child Exists For This Position");
		}
		//other wise, a child is created, with its parent already positioned
		//I set the parent node to point to the child, and vice versa.
		Node<E> tChild = new Node<E>(e, tParent, null, null);
		tParent.setLeft(tChild);
		tChild.setParent(tParent);
		//increase the size
		elements++;
		return tChild;
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	//same function as addLeft
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> tParent = check(p);
		if (right == false) {
			throw new IllegalArgumentException("Left Child Exists For This Position");
		}
		Node<E> tChild = new Node<E>(e, tParent, null, null);
		tParent.setRight(tChild);
		elements++;
		return tChild;
	}
		
	
	/* Set element in p.
	 * @return the old element in p. 
	 */
	//sets a specified element
	public E setElement(Position<E> p, E e) throws IllegalArgumentException {
		//checks the position, and saves the old element.  The parameterized element
		//is then put into the current position.
		Node<E> n = check(p);
		E l = n.getElement();
		n.setElement(e);
		return l;
	}
	

	
	/**
	 * If p has two children, throw IllegalAugumentException. 
	 * If p is an external node ( that is it has no child), remove it from the tree.
	 * If p has one child, replace it with its child. 
	 * If p is root node, update the root accordingly. 
	 * @param p who has at most one child. 
	 * @return the element stored at position p if p was removed.
	 */
	//removes a node
	public E remove(Position<E> p) throws IllegalArgumentException {
		//checks the position; updates booleans
		Node<E> n = check(p);
		//stores the old element
		E l = n.getElement();
		//if there are two children, throw an error, as instructed.
		if (right == false && left == false) {
			throw new IllegalArgumentException("Can't remove a node with two children");
		}
		//checks each child, to see which is null, since there has to be one or none.
		Node<E> nChild = n.getLeft(n);
		if (nChild == null) {
			nChild = n.getRight(n);
			}
		//if a child exists, then the child becomes the parent
		if (nChild != null) {
			nChild.setParent(n.getParent(n));
		}
		//I did not know what 'change the root accordingly' meant, so I just made it the left.
		if (n == root) {
			root = n.getLeft(n);
		}
		//other wise, if the child doesnt exist, the node is removed.
		else {
			Node<E> nParent = n.getParent(n);
			if (n == nParent.getLeft(nParent)) {
				nParent.setLeft(nChild);
			}
				else {
					nParent.setRight(nChild);
				}
			}
		elements--;
		return l;
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the in-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	
	
	//I tried to do these.  I did post order, but the array was a size of 31 and I 
	//really can not tell you why, it orders them properly though.
	
	//I would love to figure it out but my time management got messed up since 
	//carnival and its 11pm and I'm tired
	
	public List<E> inOrderElements(Position<E> p) {
		return null;
		//Hint: List is an interface. ArrayList implements List. 
		
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the pre-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> preOrderElements(Position<E> p) {
		//Hint: List is an interface. ArrayList implements List. 
		return null;
	}
	
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the post-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	
	//like I said, it does not work, but the concept is that is recursively
	//searches the left and right, then adds the element to an array. 
	//it removes the nodes as it goes.
	public List<E> postOrderElements(Position<E> p) {
		
		return null;
	}
	
	/**
	 * Return the elements in the subtree of node P, including the data in node P. 
	 * The data in the return list need to match the level-order traversal.  
	 * @param p who has at most one child. 
	 * @return the List of elements in subtree of P following the in-order traversal. 
	 */
	public List<E> levelOrderElements(Position<E> p) {
	return null;
	}

}
