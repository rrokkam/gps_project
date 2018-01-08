package coordinates;

/**
 * Represents a transformation from pixel coordinates to an abstract set
 * of coordinates.
 * @author rohithrokkam
 * @param <X> Abstract set of coordinates to convert to and from pixels
 */
public interface PixelTransformation<X extends Coordinates<?, ?>> 
		extends Transformation<X, PixelCoordinates> {

	/**
	 * Return the member of the abstract set of coordinates corresponding
	 * to the provided PixelCoordinates.
	 * @param y The pixel coordinates to be converted to abstract coordinates.
	 * @return The member of the abstract set of coordinates corresponding
	 * to the provided PixelCoordinates.
	 */
	@Override
	public X convertFrom(PixelCoordinates y);

	/**
	 * Return the member of the abstract set of coordinates corresponding
	 * to the provided PixelCoordinates.
	 * @param x The abstract coordinates to be converted to pixel coordinates.
	 * @return The pixel coordinates corresponding to the given member of the
	 * abstract set of coordinates..
	 */
	@Override
	public PixelCoordinates convertTo(X x);
	
	/**
	 * An optional operation. If this transformation exhibits noncanonical
	 * behavior that can be described by a scale factor, override that factor
	 * and return the correct value here. The default value is to return 1F.
	 * @return 1F by default.
	 */
	public default float changeFromCanonicalScaling() {
		return 1F;
	}
}