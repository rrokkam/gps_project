package state;

import directions.DirectionStrategy;
import directions.Directions;

/**
 * A mediator between the directions package and the GUI portion of the program. Creates
 * a new thread to calculate directions between specified vertices in response to state
 * changes.
 * @author rohithrokkam
 */
public class DirectionsGenerator implements StateListener {

	/* The directions strategy for this directions generator. */
	private DirectionStrategy strategy;
	
	/*  The state information of the map to be drawn. */
	private MapState state;

	/**
	 * Construct a new directions generator.
	 */
	public DirectionsGenerator(DirectionStrategy strategy, MapState state) {
		this.strategy = strategy;
		this.state = state;
	}
	
	/**
	 * Called by the MapState object when this state is updated. 
	 */
	@Override
	public void stateUpdated() {
		if(state.getNeedDirections() == true) {
			// should really create a new thread to run this bit -- Djikstra's algorithm.
			Directions directions;
			if(state.getDrivingThere())
				directions = strategy.getDirections(state.getObjectAt(), state.getEnd());
			else
				directions = strategy.getDirections(state.getStart(), state.getEnd());
			state.setNeedDirections(false); // important to call this before anything else
			// to avoid endless calls being added to stack.
			state.setDirections(directions);
		}
	}
}