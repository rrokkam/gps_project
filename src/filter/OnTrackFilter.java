package filter;

import java.util.ArrayList;
import java.util.List;

import coordinates.Spherical2DCoordinates;
import directions.Directions;
import directions.Vertex;
import openstreetmap.Node;

/**
 * A filter for Spherical2DCoordinates which can determine whether the specified
 * coordinates are on the path specified upon instantiation of this object.
 * 
 * @author rohithrokkam
 */
public class OnTrackFilter implements Filter<Spherical2DCoordinates> {

	/* How far the driver can be before triggering off-track warnings
	   in kilometers. */
	private float tolerance;
	
	/* Default tolerance. */
	private static final float DEFAULT_OFF_TRACK_DISTANCE_TOLERANCE = 0.08F;
	
	/* The list of directions for off-track distances. */
	private List<Node> nodeList;

	/**
	 * Specify a route to be checked on-track. Will ignore elements 
	 * of the directions vertices that are not Nodes.
	 * @param directions The directions to be checked against.
	 */
	public OnTrackFilter(Directions directions) {
		nodeList = new ArrayList<Node>();
		tolerance = DEFAULT_OFF_TRACK_DISTANCE_TOLERANCE;
		List<Vertex> vertexDirections = directions.asList();
		PrimitiveFilter pf = new PrimitiveFilter();
		// Do some casting so these vertices have coordinates...
		for (Vertex v : vertexDirections) {
			if (pf.satisfiesCondition(v)) {
				Node n = (Node) v;
				nodeList.add(n);
			}
		}
	}
	
	/**
	 * Specify a route to be checked on-track. Will ignore elements 
	 * of the directions vertices that are not Nodes. 
	 * @param directions The directions to be checked against.
	 * @param tolerance How far a set of coordinates can be from the directions
	 * in kilometers and still be marked as on track.
	 */
	public OnTrackFilter(Directions directions, float tolerance) {
		this.tolerance = tolerance;
		List<Vertex> vertexDirections = directions.asList();
		PrimitiveFilter pf = new PrimitiveFilter();
		// Do some casting so these vertices have coordinates...
		for (Vertex v : vertexDirections) {
			if (pf.satisfiesCondition(v)) {
				Node n = (Node) v;
				this.nodeList.add(n);
			}
		}
	}

	/**
	 * Return whether the specified coordinates are on the route.
	 * @return Whether the specified coordinates are on the route.
	 */
	@Override
	public boolean satisfiesCondition(Spherical2DCoordinates e) {
		Spherical2DCoordinates prevCoords = nodeList.get(0).coordinates();
		Spherical2DCoordinates currentCoords;
		for(int i = 1; i < nodeList.size(); i++) {
			currentCoords = nodeList.get(i).coordinates();
			if(onTrack(e, prevCoords, currentCoords))
				return true;
			prevCoords = currentCoords;
		}
		return false;
	}
	
	/**
	 * Return true if the user is following the directions, or false otherwise.
	 * Approximation is only accurate for short edges 
	 * (where edgeStart.metric().distance(edgeEnd) is small).
	 * @return True if the user is following the directions, or false otherwise.
	 */
	private boolean onTrack(Spherical2DCoordinates location, 
			Spherical2DCoordinates edgeStart, Spherical2DCoordinates edgeEnd) {
		float rise = edgeEnd.getLat() - edgeStart.getLat();
		float run = edgeEnd.getLon() - edgeStart.getLon();
		float slope = rise / run;
		float yIntercept = slope * edgeStart.getLon() + edgeStart.getLat();
		
		//Drawing the perpendicular line..
		float perpSlope = -1 / slope;
		float perpYIntercept = perpSlope * location.getLon() + location.getLat();
		
		//Finding the intersection..
		float intersectionX = (perpYIntercept - yIntercept) / (slope - perpSlope);
		float intersectionY = slope * intersectionX + yIntercept;
		
		//Creating coordinates for the intersection to take advantage of 
		//ready-made formulas to compute distance between coordinates in kilometers.
		Spherical2DCoordinates intersection = 
				new Spherical2DCoordinates(intersectionX, intersectionY);
		float distance = location.distance(intersection);		
		return distance < tolerance;
	}
}