package metric;

import coordinates.Coordinates;

/**
 * A representation of an abstract metric distance function
 * in two-dimensional space. Metrics should be singletons.
 * @param <X> The type of coordinate objects in the metric space.
 * @param <D> The return type of the distance function between member 
 * objects.
 * @author rohithrokkam
 */
public interface Metric<X extends Coordinates<?, D>, D> {
	
	/**
	 * Return the distance between two elements in two-dimensional
	 * space.
	 * @return The distance between the first and second object.
	 */
	public D distance(X first, X second);
}