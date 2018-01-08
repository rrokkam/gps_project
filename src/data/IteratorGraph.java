package data;

import java.util.List;
import java.util.Map;

import directions.Vertex;

/**
 * An IteratorMap wrapper with helper method adjacentVertices() that allows
 * clients to search through an IteratorMap of vertices as a graph.
 * @author rohithrokkam
 * @param <K> The type of vertex elements in this graph.
 */
public class IteratorGraph<K extends Vertex> extends IteratorMap<K, List<K>> {
	
	/**
	 * Warn the user if they attempt to construct an empty map, since
	 * IteratorMaps are not mutable. This constructor will always throw
	 * an UnsupportedOperationException.
	 */
	public IteratorGraph() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Construct a new IteratorMap backed by another type of map.
	 */
	public IteratorGraph(Map<K, List<K>> elements) {
		super(elements);
	}
	/**
	 * Return a list of adjacent vertices. The result is identical to the result
	 * that would occur by calling get(v).
	 * @param v The vertex whose adjacent vertices are to be found.
	 * @return A list of adjacent vertices to the vertex v.
	 */
	public List<K> adjacentVertices(Vertex v) {
		return super.get(v);
	}
}
