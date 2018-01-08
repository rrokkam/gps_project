package directions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import data.IteratorGraph;

/**
 * An implementation of Djikstra's algorithm.
 * @author rohithrokkam
 */
public class Djikstra implements DirectionStrategy {
	
	/* The set of vertices visited by the algorithm. */
	private Map<Vertex, Float> visited;
	
	/* For each vertex key, the value is the vertex on the shortest 
	   route from the key to the start path adjacent to the key. */
	private Map<Vertex, Vertex> predecessors;
	
	/* The set of vertices marked for future visiting by the algorithm. */
	private Map<Vertex, Float> toVisit;
	
	/* The graph for computing directions. */
	private IteratorGraph<? extends Vertex> graph;

	/**
	 * Sets up for the execution of Djikstra's algorithm.
	 * @param start The starting vertex for the search.
	 * @param end The ending vertex for the search.
	 */
	public Djikstra(IteratorGraph<? extends Vertex> graph) {
		visited = new HashMap<Vertex, Float>();
		predecessors = new HashMap<Vertex, Vertex>();
		toVisit = new HashMap<Vertex, Float>();
		this.graph = graph;
	}
	
	/**
	 * Returns the list of vertices which comprise the shortest path from the start vertex
	 * to the end vertex.
	 * @return The list of vertices which comprise the shortest path from the start vertex
	 * to the end vertex.
	 */
	public Directions getDirections(Vertex start, Vertex end) {
		if(start == null || end == null)
			return Directions.NO_DIRECTIONS;
		visited.put(start, 0F);
		predecessors.put(start, null);
		addAdjacentVerticesToVisit(start);
		System.out.println(toVisit);
		Vertex currentVertex;
		do {
			currentVertex = getNextVertex(); //Find the next vertex to search
			if(currentVertex == null)	{		//There is no valid route to the endpoint.
				clearAll();
				return Directions.NO_DIRECTIONS;
			}
			if (end.equals(currentVertex)) { 		//We found the endpoint, so we're done.
				return new Directions(getRouteTo(currentVertex)); //Get the route from start to end based on
			}								//the state of the predecessor map.
			visited.put(currentVertex, toVisit.remove(currentVertex));
			addAdjacentVerticesToVisit(currentVertex);
			
		} while(!(toVisit.isEmpty()));
		System.out.print("empty tovisit - could not find route");
		clearAll();
		return Directions.NO_DIRECTIONS;
	}

	/**
	 * Clear the hashtables of information from a run.
	 */
	private void clearAll() {
		visited.clear();
		predecessors.clear();
		toVisit.clear();
	}

	/**
	 * Adds the vertices adjacent to the specified vertex to the list of vertices to visit,
	 * along with their distances from the start point.
	 * @param v The vertex whose adjacent vertices are to be added.
	 */
	private void addAdjacentVerticesToVisit(Vertex v) {
		for(Vertex adjVertex : graph.adjacentVertices(v)) { // iterating over vertices connected to this one.
//			System.out.println(adjVertex);
//			System.out.println(v);
//			System.out.println(graph.adjacentVertices(v));
			// Float oldDistance = toVisit.get(adjVertex);
			if(visited.get(adjVertex) == null) {	   // true if the adjacent vertex has never been checked.
				System.out.println(adjVertex);
				float newDistance = visited.get(v) + v.distance(adjVertex);
				Float oldDistance = toVisit.get(adjVertex);
				if(oldDistance == null || newDistance < oldDistance)
					toVisit.put(adjVertex, newDistance);
					predecessors.put(adjVertex, v);
			}
		}
	}

	/**
	 * Returns a list of vertices representing the route from the start vertex to the
	 * specified vertex v.
	 * @param v The vertex to reach from the start vertex.
	 * @return A list of vertices containing the route from the start vertex to the
	 * specified vertex v.
	 */
	private List<Vertex> getRouteTo(Vertex v) {
		List<Vertex> route = new LinkedList<Vertex>();
		do {
			route.add(v);
			v = predecessors.get(v);
		} while(v != null);
		Collections.reverse(route);
		clearAll();
		return route;
	}

	/**
	 * Returns null if there are no eligible vertices, and the vertex with the lowest
	 * float value otherwise.
	 * @return null if there are no eligible vertices, and the vertex with the lowest
	 * float value otherwise.
	 */
	private Vertex getNextVertex() {
		Float minDistance = Float.MAX_VALUE;
		Vertex nextVertex = null;
		for(Iterator<Map.Entry<Vertex, Float>> it = toVisit.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Vertex, Float> entry = it.next();
			Vertex v = entry.getKey();
			if(visited.get(v) == null) {
				float dist = entry.getValue();
				if(dist < minDistance) {
					minDistance = dist;
					nextVertex = entry.getKey();
				}
			}
		}
		return nextVertex;
	}
}