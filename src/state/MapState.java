package state;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.starkeffect.highway.GPSEvent;
import com.starkeffect.highway.GPSListener;

import coordinates.Spherical2DCoordinates;
import data.IteratorMap;
import directions.Directions;
import elements.Element;
import openstreetmap.Node;

/**
 * A class which holds state information about the map.
 * Acts as a container for various state-related information.
 * This class also holds a utility method to find the element
 * nearest to a specified set of coordinates.
 * @author rohithrokkam
 */
public class MapState implements GPSListener {

	/* The views observing the map's state. */
	private ArrayList<StateListener> listeners;

	/* The current start for driving directions. */
	private Node start = null;

	/* The current end for driving directions. */
	private Node end = null;

	/* A flag which indicates whether to use the current location or
	 * the start location as a start point for directions. */
	private boolean drivingThere = false;

	/* The last GPS event provided by the GPSDevice. */
	private GPSEvent lastEvent = null;

	/* The set of directions to be displayed. */
	private Directions directions = null;
	
	/* A flag indicating whether directions need to be computed/recomputed. */
	private boolean needNewDirections = false;

	/* The map whose state this is. */
	private IteratorMap<String, Element> map;

	/* The nearest object to the client when the directions were last computed. */
	private Element lastElementAt;
	
	/**
	 * Construct an object representing state information about a map.
	 */
	public MapState(IteratorMap<String, Element> map) {
		this.map = map;
		listeners = new ArrayList<StateListener>();
		lastElementAt = null;
	}

	/** Get the currently set start location. */
	public Node getStart() { return start; }
	
	/**
	 * Set a new start location.
	 * @param e The location to set as start.
	 */
	public void setStart(Node e) {
		if(e == null|| !e.equals(start)) {
			start = e;
			updateListeners();
		}
	}
	
	/** Get the currently set end location. */
	public Node getEnd() { return end; }

	/**
	 * Set a new end location.
	 * @param e The location to set as end.
	 */
	public void setEnd(Node e) {
		if(e == null|| !e.equals(end)) {
			end = e;
			updateListeners();
		}
	}
	
	/**
	 * Return the current set of directions.
	 * @return The current set of directions.
	 */
	public Directions getDirections() { return directions; }

	/**
	 * Set a new set of vertices as the directions.
	 * @param directions The new directions to be set.
	 */
	public void setDirections(Directions directions) {
		this.directions = directions;
		// Don't call for directions until the nearest object to the client
		// is a different one than last time.
		if(lastElementAt != null && Directions.NO_DIRECTIONS.equals(directions)) {
			Element elementAt = getObjectAt();
			if(!lastElementAt.equals(elementAt)) {
				lastElementAt = elementAt;
				setNeedDirections(true);
			}
		}
		updateListeners();
	}

	/**
	 * Get the flag indicating whether directions need to be recomputed.
	 * @return The flag indicating whether directions need to be recomputed.
	 */
	public boolean getNeedDirections() { return needNewDirections; }

	/**
	 * Set the flag indicating whether directions need to be recomputed.
	 * @param needNewDirections Whether directions now need to be recomputed.
	 */
	public void setNeedDirections(boolean needNewDirections) {
		this.needNewDirections = needNewDirections;
		updateListeners();
	}

	/**
	 * Return whether the user's current location is to be marked.
	 * @return Whether the user's current location is to be marked.
	 */
	public boolean getDrivingThere() { return drivingThere; }

	/**
	 * Set a flag indicating whether the user's current location 
	 * is to be marked.
	 * @param drivingThere A flag indicating whether the user's 
	 * current location is to be marked.
	 */
	public void setDrivingThere(boolean drivingThere) {
		this.drivingThere = drivingThere;
		updateListeners();
	}
	
	/**
	 * Return the last date at which the client was observed.
	 * @return The last date at which the client was observed.
	 */
	public Date getLastDate() {
		if(lastEvent == null)
			return null;
		return lastEvent.getDate(); 
	}
	
	/**
	 * Return the last known heading of the client.
	 * @return The last known heading of the client.
	 */
	public double getLastHeading() { 
		if(lastEvent == null)
			return Double.NaN;
		return lastEvent.getHeading();
	}
	
	/**
	 * Returns the last known set of coordinates of the client.
	 * @return The last known set of coordinates of the client.
	 */
	public Spherical2DCoordinates getLastCoordinates() {
		if(lastEvent == null)
			return null;
		return new Spherical2DCoordinates(
				(float) lastEvent.getLongitude(), 
				(float) lastEvent.getLatitude());
	}
	
	/**
	 * Attempts to return the nearest map element to the specified
	 * location. If no such element is found, returns null. If multiple objects
	 * are equally nearby, returns an arbitrary one of those objects. That
	 * returned object may not be consistent with repeated calls.
	 * 
	 * This is currently a highly inefficient method (O(n)) and calls to this
	 * should be minimized as much as possible.
	 */
	public Element getObjectAt(Spherical2DCoordinates sc) {
		float minDistance = Float.POSITIVE_INFINITY;
		Element nearestObject = null;

		//Replace with Bounds Filter to make this efficient.
		for (Iterator<Element> it = map.values().iterator(); it.hasNext();) {
			Element next = it.next();
			Spherical2DCoordinates otherSC = next.coordinates();
			if(otherSC != null) {
				float distance = sc.metric().distance(sc, otherSC);
				if (distance < minDistance) {
					nearestObject = next;
					minDistance = distance;
				}
			}
		}
		return nearestObject;
	}
	
	/**
	 * An auxiliary getObjectAt method which uses the client's current location
	 * as a default. Returns null if the last coordinates of the client have not
	 * been set with a GPSEvent.
	 * @return The object at the client's current position, or null if that position
	 * is unknown.
	 */
	public Element getObjectAt() {
		if(getLastCoordinates() == null)
			return null;
		return getObjectAt(getLastCoordinates());
	}
	
	/**
	 * Reset all state information to the defaults.
	 */
	public void clear() {
		start = null;
		end = null;
		directions = null;
		needNewDirections = false;
		drivingThere = false;
		updateListeners();
	}
	
	/**
	 * Called by a GPSDevice to indicate a new location has been set.
	 */
	@Override
	public void processEvent(GPSEvent e) {
		lastEvent = e;
		if(drivingThere)
			updateListeners(); // handle direction checking here
	}
	
	/**
	 * Update the views listening to this state about changes
	 * made to this state.
	 */
	private void updateListeners() {
		for(StateListener l : listeners) {
			l.stateUpdated();
		}	
		System.out.println(toString()); // Debugging printout.
	}

	/**
	 * Register a view.
	 * @param v The view to be registered.
	 * @return True if the addition was successful, false otherwise.
	 */
	public boolean register(StateListener l) {
		return listeners.add(l);
	}

	/**
	 * Deregister a view.
	 * @param v The view to be deregistered.
	 * @return True if the removal was successful, false otherwise.
	 */
	public boolean deregister(StateListener l) {
		return listeners.remove(l);
	}

	/**
	 * For debugging, display the state of this MapState.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Views:");
		sb.append(listeners.toString());
		sb.append("\n");
		sb.append("Start:");
		if(start == null)
			sb.append("Null");
		else
			sb.append(start.toString());
		sb.append("\n");
		sb.append("End:");
		if(end == null)
			sb.append("Null");
		else
			sb.append(end.toString());
		sb.append("\n");
		sb.append("Drive there mode: " + drivingThere);
		sb.append("\n");
		sb.append("Last received GPSEvent:");
		if(lastEvent == null)
			sb.append("Null");
		else
			sb.append(lastEvent.toString());
		sb.append("\n");
		sb.append("Current directions:");
		if(directions == null)
			sb.append("Null");
		else
		sb.append(directions.toString());
		sb.append("Need new directions: " + needNewDirections);
		return sb.toString();
	}
}