package state;

/**
 * Interface implemented by objects that can be managed by a controller.
 * @author rohithrokkam
 */
public interface StateListener {

	/**
	 * Method called to notify this view that the state has been updated.
	 */
	void stateUpdated();
}