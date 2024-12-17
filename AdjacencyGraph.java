package cs2321;
import cs2321.HashMap;  
import net.datastructures.*;

/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * @author CS2321 Instructor
 */
public class AdjacencyGraph<V, E> implements  Graph<V, E> {
	//Vertex Class
	private static class myVertex<V,E> implements Vertex<V> {
		//each vertex has an element, position, and a hashMap to keep track of the in and out degree
		V element;
		Position<Vertex<V>> pos;
		HashMap<Vertex<V>, Edge<E>> in;
		HashMap<Vertex<V>, Edge<E>> out;
		//a vertex is instantiated with a generic element, and a boolean to determine if it is directed or not.
		public myVertex(V e, boolean directed) {
			element = e;
			out = new HashMap<>();
			//if the graph is directed, it needs an in degree, if it is not directed, then in can just be the out map,
			//because they are the same.
			if (directed) {
				in = new HashMap<>();
			}
			else {
				in = out;
			}
		}
		
		//getters and setters
		@Override
		public V getElement() {
			return element;
		}
	
		public void setPosition(Position<Vertex<V>> p) { pos = p; }
		   public Position<Vertex<V>> getPosition( ) { return pos; }
		   public void setElement(V v) {element = v;}
		   public Map<Vertex<V>, Edge<E>> getOutgoing( ) { return out; }
		   public Map<Vertex<V>, Edge<E>> getIncoming( ) { return in; }
		
	}
	//Edge class
	private static class myEdge<V,E> implements Edge<E> {
		//Each edge has an element, a position, and a vertex array that stores the vertices its between.
		E element;
		Position<Edge<E>> pos = null;
		Vertex<V>[] ends;
		public myEdge(Vertex<V> v1, Vertex<V> v2, E v) {
			//ends holds two elements, ends[0], which is the first vertex its between, and ends[1] which is the last vertex.
			ends = (Vertex<V>[]) new Vertex[] {v1,v2};
			element = v;
		}
		
		//getters and setters.
		@Override
		public E getElement() {
			return element;
		}
		public void setElement(E e) {
			element = e;
		}
		public Vertex<V>[ ] getEndpoints( ) { 
			return ends; 
			}
		public void setPosition(Position<Edge<E>> p) { 
			pos = p; 
			}
		public Position<Edge<E>> getPosition( ) { 
			return pos; 
			}
	}
	
	//start of graph class 
	//create two lists of vertices and edges.  These simply store all the elements.
	DoublyLinkedList <Vertex<V>> vertices = new DoublyLinkedList<>();
	DoublyLinkedList <Edge<E>> edges = new DoublyLinkedList<>();
	//boolean that stores if the graph is directed or not, based on the constructor.
	boolean isDirected;
	//constructor for a directed graph, isDirected is whatever the boolean is.
	public AdjacencyGraph(boolean directed) {
		isDirected = directed;
	}
	//I honestly didn't know what to do with this constructor, but I guessed that the default graph would be undirected
	public AdjacencyGraph() {
		isDirected = false;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 */
	//iterator for edges, returns the DoublyLinkedList iterator for edges.
	//TODO: replace ??? with the worst case running time complexity for the method
	@TimeComplexity(" O(e) ")
	public Iterable<Edge<E>> edges() {
		/* TCJ: iterating the number of edges will take however many edges there are in the graph, notated e.
		 */
		// TODO Auto-generated method stub
		return edges;
	}

	//two helper methods for the whole class.  validatE, and Validate.  validatE, with the capital E checks
	//for safe edges.  It also casts edges to the myEdge class so we can access the getters and setters.
	private myEdge<V,E> validatE(Edge<E> e) throws IllegalArgumentException {
		myEdge<V, E> p = (myEdge) e;
		if (p.getPosition() == null) {
			throw new IllegalArgumentException("position is invalid");
		}
		return p;
	}
	//Validate with the capital V casts Vertices to myVertice.  It also checks for safe vertices. 
	private myVertex<V,E> Validate(Vertex<V> v) throws IllegalArgumentException {
		myVertex<V,E> p = (myVertex) v;
		if (p == null || p.getPosition() == null) {
			throw new IllegalArgumentException("position is invalid");
		}
		return p;
	}
	
	
	public Vertex<V> getVertex(V v) {
		for (Vertex<V> f : vertices()) {
			if (f.getElement().equals(v)) {
				return f;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 */
	//validates the edge, and returns the ends of that array.
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	public Vertex[] endVertices(Edge<E> e) throws IllegalArgumentException {
			/* TCJ: This method is just a cast and a get, which are constant time operations, so this is also o(1).
			 */
		myEdge<V,E> p = validatE(e);
		return p.getEndpoints();
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 */
	//insert edge between two vertices.
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(1)")
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws IllegalArgumentException {
			/* TCJ: Other than casting, the only actions taking place is addLast and put.  AddLast for positional  
			 * list is o(1), and put is also o(1) with a good hashmap, leaving an expected
			 * time complexity of o(1).  With the hashMap I have implemented, the worst case
			 * is o(1), but for a worst case scenario it could be upwards to o(n).  
			 */
		//use getEdge to get the edge.  If the edge doesn't exist, throw an error that the edge exists.
		if (getEdge(u,v) == null) {
			//make a myEdge, because we have established that the edge doesn't exist.
			myEdge<V,E> newO = new myEdge(u, v, o);
			//addLast returns a position, we can add the edge to the edge list, and set its position to this very maneuver.
			newO.setPosition(edges.addLast(newO));
			//cast the two vertices to myVertices.
			myVertex<V,E> v1 = Validate(u);
			myVertex<V,E> v2 = Validate(v);
			// because of the way we set up the hashMaps, we can add the first vertex to outgoing, and
			//the second to ingoing.  This will work for directed, because the degree of the vertex has gone up
			//one on each side, and for directed, in = out, so the degree has increased two and both vertices
			//are accounted for.
			v1.getOutgoing().put(v, newO);
			v2.getIncoming().put(u, newO);
			//return the edge we just added.
			return newO;
		}
		else {
			throw new IllegalArgumentException("edge exists");	
		}
		
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 */
		//insert vertex is just creating a vertex
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	public Vertex<V> insertVertex(V o) {
		// TODO Auto-generated method stub
		/*TCJ
		 * There are literally two lines of code, and one is a cast.  The only other action is 
		 * addLast, which is o(1) for a positional list.
		 */
		//make a new myVertex with element o.  Use boolean isDirected to determine if it is directed
		myVertex<V,E> v = new myVertex(o, isDirected);
		//use the setPoistion tactic, that stores the position and adds the vertex to the vertex list.
		v.setPosition(vertices.addLast(v));
		//return the vertex
		return v;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 */
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	//the size of the edge list is effectively the number of edges, so we can return edgeList.size
	public int numEdges() {
			/* TCJ: we are returning a constant time operation, so this is also o(1).
			 */
		// TODO Auto-generated method stub
		return edges.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 */
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	//the same method for counting edges applies to vertices.
	public int numVertices() {
			/* TCJ: we are returning a constant time operation, so this is also o(1).
			 */
		// TODO Auto-generated method stub
		return vertices.size();
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		//cast the edge to myEdge to access getters and setters.
		myEdge<V,E> newE = validatE(e);
		//make a vertex array so we can access its elements.
		Vertex<V>[] ends = newE.getEndpoints();
		//if the first index is our parameterized vertex, return the other vertex, or ends[1].
		if (ends[0] == v) {
			return ends[1];
		}
		//vice verse, if its the other index, return 0.
		else if (ends[1] == v) {
			return ends[0];
		}
		//since these are the only two cases possible, if the edge and vertex are related, we must have found
		//the opposite by now.  If not, then throw an error saying the vertex and edge are correlated.
		else {
			throw new IllegalArgumentException("the vertex and edge are not related");
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(1)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		/* TCJ: There are three casts, one get, and 3 removes.  One remove is that of a positional list, and with
		 * a position, this is o(1).  The remove method for the hashmap is also o(1) with a good
		 * 	hash function, which we have, so this is expected as o(1), but with a bad hashmap, can be upwards to o(n)
		 */
		//cast and edge to myEdge to access methods.
		myEdge<V,E> t = validatE(e);
		//create a vertex array so we can access its elements.
		Vertex<V>[] v = t.getEndpoints();
		//validate both vertices in the array so we can get their hashmaps. 
		myVertex<V,E> v1 = Validate(v[0]);
		myVertex<V,E> v2 = Validate(v[1]);
		//remove the vertices from the map
		v1.getOutgoing().remove(v2);
		v2.getIncoming().remove(v1);
		//remove the edge from the edge list from the positional list.
		edges.remove(t.getPosition());
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 */
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(n) ")
		//TODO: replace XXX with the expected  running time complexity for the method
		@TimeComplexityExpected(" O(degree(v))")
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		/* TCJ: This one has a funny time complexity that I don't really know 
		 * how to notate.  There are two for loops that have to iterate, based on the number 
		 * of edges attached to the vertex.  This is also known as the degree.  For an undirected graph, out = in, so 
		 * all the edges are in out, and this method is o(deg(v)).  For a directed, we also need to get the indegree.
		 * This is equivalent to the total degree, but we can only iterate this loop if it is directed.  All in all, 
		 * the expected tcd is what the degree of the removed vertex is.
		 */
		//cast the vertex to myvertex
		myVertex<V,E> newV = Validate(v);
		//for both undirected and directed graphs, we need to remove all edges attached to it.  
		//we can do this by using the values() iterator for a hash map, because the value of the hashmap 
		//is the edge attached to vertex.  For every edge in this list, we remove it, because the edge is pending
		//missing a vertex.
		for (Edge<E> e: newV.getOutgoing().values()) {
			removeEdge(e);
		}
		//if the graph is directed, we still have some of the edges to remove.  We use the same way we just
		//used for outgoing edges, but for incoming.
		if (isDirected) {
		for(Edge<E> e : newV.getIncoming().values()) {
			removeEdge(e);
		}
		}
		//remove the vertex from the list using its position.
		vertices.remove(newV.getPosition());
	}

	/* 
     * replace the element in edge object, return the old element
     */
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		//cast the edge
		myEdge<V,E> newE = validatE(e);
		//get the old element
		E old = newE.getElement();
		//set the new element
		newE.setElement(o);
		//return the old element
		return old;
	}

    /* 
     * replace the element in vertex object, return the old element
     */
	
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		//cast the vertex
		myVertex<V,E> newV = Validate(v);
		//get the old element
		V old = newV.getElement();
		//set the new element
		newV.setElement(o);
		//return the old element
		return old;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 */
	//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(v) ")
	public Iterable<Vertex<V>> vertices() {
		/* TCJ: iterating this method will take however many vertices are in the graph.
		 */
		// TODO Auto-generated method stub
			//return the DoublyLinkedList iterator of vertices.
		return vertices;
	}
		
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	@Override
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		/* TCJ: we are returning a constant time operation of size(), so this is also constant time
		 */
		// TODO Auto-generated method stub
			//cast the vertex
		myVertex<V,E> newV = Validate(v);
		//return the hashmap size of outgoing
		return newV.getOutgoing().size();
	}
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
			/* TCJ: we are returning a constant time operation of size(), so this is also constant time
			 */
		// TODO Auto-generated method stub
			//cast the vertex
		myVertex<V,E> newV = Validate(v);
		//return the hashmap size of ingoing.
		return newV.getIncoming().size();
	}
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(deg(v)) ")
	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
			throws IllegalArgumentException {
			/* TCJ: we are iterating the hashmap of the outgoing edges, so this will take the degree of v, if the 
			 * graph is undirected, or the outDegree of v if the graph is directed.
			 */
			//cast the vertex
		myVertex<V,E> newV = Validate(v);
		//return the values() iterator in the map interface that stores the edges.
		return newV.getOutgoing().values();
	}
		//TODO: replace ??? with the worst case running time complexity for the method
		@TimeComplexity(" O(1) ")
	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
			throws IllegalArgumentException {
			/* TCJ: we are iterating the hashmap of the incoming edges, so this will take the degree of v if the graph
			 * is undirected, or the inDegree of v if it is directed
			 */
			//cast the vertex
		myVertex<V,E> newV = Validate(v);
		//return the values() iterator in the map interface that stores the edges.
		return newV.getIncoming().values();
	}
	
	//TODO: replace XXX with the expected  running time complexity for the method
	@TimeComplexityExpected(" O(1)")
	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
			throws IllegalArgumentException {
		myVertex<V,E> first = Validate(u);
		for (Edge<E> e : outgoingEdges(first)) {
			Vertex<V> op = opposite(first, e);
			if (op.equals(v)) {
				return e;
			}
		}
		/* TCJ: We are searching for an edge between two vertices.  This is done most efficiently using 
		 * hashmaps with instant retrieval.  Getting the means we have to get the pair of the other vertex and the value 
		 * which is the edge.  With a good hashmap, this is o(1).
		 */
		//cast the vertex, either one is okay, I choose u.
		return null;
	}
	
}
