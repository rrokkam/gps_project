package state;

import coordinates.Spherical2DCoordinates;
import directions.Directions;
import filter.OnTrackFilter;

/**
 * A listener for a MapState object which can indicate whether the
 * client's last event is on or off track.
 * @author rohithrokkam
 */
public class OnTrackChecker implements StateListener {

	/* The state information of the map. */
	private MapState state;

	/* The on track filter for this checker. */
	private OnTrackFilter filter;
	
	/* The current set of directions for this checker. */
	private Directions directions;
	
	/**
	 * Construct a new track checker.
	 * @param state The mapstate which this checker is a listener of.
	 */
	public OnTrackChecker(MapState state) {
		this.state = state;
		filter = null;
		directions = null;
	}
	
	/**
	 * If the client is off track, request for a new set of directions.
	 */
	@Override
	public void stateUpdated() {
		Directions d = state.getDirections();
		if(state.getDrivingThere() && (d != null)) {
			if(!d.equals(directions)) {
				filter = new OnTrackFilter(d);
			}
			Spherical2DCoordinates userSC = state.getLastCoordinates();
			if(filter.satisfiesCondition(userSC))
				return;
			else { 
				// Need to make a new set of directions.
				state.setNeedDirections(true);
			}
		}
	}
}