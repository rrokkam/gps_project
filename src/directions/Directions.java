package directions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A class which represents the directions found by a directionsGenerator.
 * @author rohithrokkam
 */
public class Directions implements Iterable<Vertex> {

	/* The list of nodes which constitute these directions. */
	private final List<Vertex> vertices;
	
	/* A value representing that no directions were found. */
	public static final Directions NO_DIRECTIONS =
			new Directions(new ArrayList<Vertex>());
	/**
	 * Construct a new set of directions from a list of nodes.
	 * @param list The member elements of the set of directions.
	 */
	public Directions(List<Vertex> list) {
		vertices = Collections.unmodifiableList(list);
	}

	/**
	 * Return an iterator over the vertices in this set of directions.
	 * @return An iterator over the vertices in this set of directions. 
	 */
	@Override
	public Iterator<Vertex> iterator() {
		return vertices.iterator();
	}
	
	/**
	 * Return an unmodifiable list version of the directions 
	 * represented by this object.
	 * @return An unmodifiable list version of the directions 
	 * represented by this object.
	 */
	public List<Vertex> asList() {
		return Collections.unmodifiableList(vertices);
	}
	/**
	 * Return a String representation of these directions.
	 * @return A String representation of these directions.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// add in the directions strings
		Vertex prevVertex = vertices.get(0);
		Vertex nextVertex;
		sb.append(prevVertex.toString());
		for(int i = 1; i < vertices.size(); i++) {
			nextVertex = vertices.get(i);
			sb.append(nextVertex.toString());
			sb.append("; Distance = ");
			sb.append(prevVertex.distance(nextVertex));
			sb.append("\n");
			prevVertex = nextVertex;
		}
		return sb.toString();
	}
}