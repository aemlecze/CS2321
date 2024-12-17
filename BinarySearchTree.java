package cs2321;
import java.util.ArrayList;
import net.datastructures.Entry;
import net.datastructures.*;
import net.datastructures.BinaryTree;
import net.datastructures.SortedMap;
import cs2321.LinkedBinaryTree;
import java.util.Iterator;
public class BinarySearchTree<K extends Comparable<K>,V> extends AbstractMap<K,V> {
	
	
	// TODO: Copy your implementation of LinkedBinaryTree to this project 
	//      or use the cs2321util archive
	//      Then uncomment this line
	
	/* The base data structure is a linked binary tree with the leaf nodes used as sentinel nodes*/
	LinkedBinaryTree<Entry<K,V>> tree; 
	
	int size;  //the number of entries (mappings)
	
	/* 
	 * default constructor
	 */
	//constructor for tree.  The tree is just a binary tree, but with special
	//properties, so we can instantiate a linked binary tree.  The size 
	//is 0, and we add a root with no value.
	public BinarySearchTree() {
		tree = new LinkedBinaryTree<>();
		size = 0;
		tree.addRoot(null);
	}
	
	/* 
	 * Return the tree. The purpose of this method is purely for testing.  
	 */
	//no comment
	public BinaryTree<Entry<K,V>> getTree() {
		
		// TODO return the base tree data structure which contains the data nodes and sentinel nodes. 
		return tree;
	}
	
	@Override
	//returns size
	public int size(){
		// TODO Auto-generated method stub
		return size;
	}
	//binary search method.
	public Position<Entry<K,V>> search(Position<Entry<K,V>> p, K key) {
		//if the position no longer has a sub tree, the position is returned.
		//this means no position was found.
		if (tree.isExternal(p)) {
			return p;
		}
		//make a return value that compares the key to the current positions key.
		int ret = key.compareTo(p.getElement().getKey());
		//if they are equal, we can return that position.
		if (ret == 0) {
			return p;
		}
		//if the key is smaller, we traverse the left sub tree
		else if (ret < 1) {
			//recursively search this tree
			return search(tree.left(p), key);
		}
		//if its bigger we traverse the right sub tree
		else {
			//recursively search this tree
			return search(tree.right(p), key);
		}
	}
	@Override
	//get method, returns value
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(logn)")
	public V get(K key) {
		/* TCD
		 * The get method has a worse case of n, and an average case of logn.
		 * To be more specific, the it would be O(h), where h is height, which usually
		 * pans out to be logn.  This can be calculated out to get
		 *  log(n+1) - 1 <= h <= n -1.  If the values of the BST are increasingly bigger
		 *  or smaller, it is o(n), otherwise, it would be an amortied cost of o(logn)
		 * 
		 */
		// we will search for a position that has this key
		Position<Entry<K,V>> p = search(tree.root(), key);
		//based on the search method, a position being external means nothing was found
		//so if it is external, we return null, meaning the key doesn't exist.
		if (tree.isExternal(p)) {
			return null;
		}
		//otherwise, if the key exists, we return the value of the key value pair.
		else {
			return p.getElement().getValue();
		}
	}
	//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(log(n))")
	@Override
	public V put(K key, V value) {
		/*TCD
		 * put has the same concept of worst case o(n), but commonly o(h), or o(logn)
		 * the only function that utilizes a lot of time is search, which depends
		 * on the tree, but amortized time cost is o(logn).
		 */
		//there are two cases we need to look out for, the key not existing
		//or the key existing.  We search for the key using search
		Position<Entry<K,V>> W = search(tree.root(), key);
		//if the key doesn't exist, we need to insert it at the end.
		if (tree.isExternal(W)) {
			//make an entry of the key value pair
			Entry<K,V> entry = new mapEntry<K,V>(key, value);
			//we set p, the sentinel node, to the element of entry.
			tree.setElement(W, entry);
			//we make new sentinel nodes, with a null value.
			tree.addLeft(W, null);
			tree.addRight(W, null);
			//increase size.
			size++;
			//return nothing
			return null;
		}
		//if the key does exist, we need to update the value.
		else {
			Entry<K,V> entry = new mapEntry<K,V>(key, value);
			//v, the return value is the old value
			V v = W.getElement().getValue();
			//we update the value just by making the position equal to the
			//key value pair, the keys are the same, so nothing is 
			//messed up with the BST, and the value updates.
			tree.setElement(W, entry);
			//return the old value.
			return v;
		}
	}
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(log(n))")
	@Override
	public V remove(K key) {
			/* TCD
			 * Very similar to get, because we are getting an element to remove
			 * if the element doesn't exist, 'retrieving' the position
			 * takes upwards to log n, but usually would be o(h), h being the height 
			 * of the position being removed, which is o(logn).
			 */
		// TODO Auto-generated method stub	
		Position<Entry<K,V>> W = search(tree.root(), key);
		if (W == null || W.getElement() == null) {
			return null;
		}
		V v = W.getElement().getValue();
		Position<Entry<K,V>> left = tree.left(W);
		Position<Entry<K,V>> right = tree.right(W);
		if (tree.isExternal(left)) {
			tree.remove(left);
			tree.remove(W);
			return W.getElement().getValue();
		}
		if (tree.isExternal(right)) {
			tree.remove(right);
			tree.remove(W);
			return W.getElement().getValue();
		}
		while (tree.isInternal(right)) {
			right = tree.left(right);
		}
		Position<Entry<K,V>> successor = tree.parent(right);
		tree.setElement(W, successor.getElement());
		tree.remove(right);
		tree.remove(successor);
		return v;
	}
	
	@Override
	//returns an iterable tree entrySet().
	public Iterable<Entry<K, V>> entrySet() {
		Iterator<Entry<K,V>> i = ((Iterable<Entry<K, V>>) tree).iterator();
		return (Iterable<Entry<K, V>>) i;
	}



	@Override
	//returns if tree is empty
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	

}
