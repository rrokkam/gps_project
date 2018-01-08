package directions;

/**
 * An interface representing objects which can be represented as vertices
 * on a weighted graph.
 * @author rohithrokkam
 */
public interface Vertex {

	/**
	 * Return true if the object provided is equivalent to this one, and false otherwise.
	 * @param o The object to be compared to this object.
	 * @return True if the object provided is equivalent to this one, and false otherwise.
	 */
	@Override
	public boolean equals(Object o);
	
	/**
	 * Return the distance between this vertex and the specified other vertex.
	 * @param v The vertex whose distance from this vertex is to be found.
	 * @return Return the distance between this vertex and the specified other vertex.
	 */
	public float distance(Vertex v);
}
