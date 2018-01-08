package directions;

import java.util.Collection;
import java.util.HashMap;

/**
 * A temporary stub implementation of Vertex which was written to test the
 * Djikstra's algorithm independently of the OpenStreetMap application.
 * This class is defunct since Vertex no longer requires adjacentVertices()
 * as part of its interface.
 * @author rohithrokkam
 */
public class VertexNode implements Vertex {
	
	// yes this field is package private, it's only for testing.
	/* Distances from this node to other nodes. */
	HashMap<Vertex, Float> distances; 
	
	/**
	 * Make a new Node.
	 */
	public VertexNode() {
		distances = new HashMap<Vertex, Float>();
	}

	/**
	 * Return the distance between this vertex and the other vertex.
	 * Bad things will happen if this is null.
	 * @return The distance between this vertex and the other vertex.
	 */
	@Override
	public float distance(Vertex v) {
		return distances.get(v);
	}

	/**
	 * Return the vertices that are adjacent to this one.
	 * @return The vertices that are adjacent to this one.
	 */
	public Collection<Vertex> adjacentVertices() {
		return distances.keySet();
	}
}