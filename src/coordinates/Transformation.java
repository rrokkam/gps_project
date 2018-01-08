package coordinates;

/**
 * Represents a transformation from elements of one set of coordinates to another.
 * @author rohithrokkam
 *
 * @param <X> The first space of the transformation.
 * @param <Y> The second space of the transformation.
 */
public interface Transformation<X extends Coordinates<?, ?>, Y extends Coordinates<?, ?>> {

	/**
	 * Returns the element in Y corresponding to the provided element in X.
	 * @param x The element in X to be converted to an element of Y.
	 * @return The element in Y corresponding to the provided element in X.
	 */
	public Y convertTo(X x);
	
	/**
	 * Returns the element in X corresponding to the provided element in Y.
	 * @param y The element in Y to be converted to an element of X.
	 * @return The element in X corresponding to the provided element in Y.
	 */
	public X convertFrom(Y y);
	
}
