package coordinates;

import metric.Metric;

/**
 * An interface representing members of a finite dimensional metric space.
 * @author rohithrokkam
 * @param <E> The type of the member coordinate entries.
 * @param <D> The type of the return value of the distance metric.
 */
public interface Coordinates<E, D> {

	/**
	 * Return the coordinate of this member corresponding to the
	 * number given by index.
	 * @param index The index of the coordinate to be returned.
	 * @return The coordinate corresponding to the provided index.
	 */
	public E coordinate(int index);

	/**
	 * Return the coordinates represented by this object.
	 * @return The coordinates represented by this object.
	 */
	public E[] coordinates();

	/**
	 * Returns the distance between these coordinates and 
	 * another set of coordinates, if the comparison is applicable.
	 * Else, returns null.
	 * @return The distance between these coordinates and 
	 * another set of coordinates, or null if the comparison is not
	 * applicable.
	 */
	public D distance(Coordinates<E, D> other);

	/**
	 * Returns the metric associated with the coordinate space
	 * of these coordinates.
	 * @return The metric associated with the coordinate space
	 * of these coordinates.
	 */
	public Metric<? extends Coordinates<E, D>, D> metric();
}
