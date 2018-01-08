package coordinates;

import metric.EuclideanMetric;

/**
 * Members of this class represent coordinates on a 2D set of pixels.
 * @author rohithrokkam
 */
public class PixelCoordinates implements Coordinates<Integer, Float> {

	/* The x-coordinate of this element. */
	private int x;

	/* The y-coordinate of this element. */
	private int y;

	/* The metric for the space of pixel coordinates. */
	private static final EuclideanMetric metric = EuclideanMetric.getInstance();
	
	/**
	 * Construct a new set of pixel coordinates.
	 */
	public PixelCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x-coordinate of these coordinates.
	 * @return The x-coordinate of these coordinates.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y-coordinate of these coordinates.
	 * @return The y-coordinate of these coordinates.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer coordinate(int index) {
		if (index == 0)
			return x;
		else if (index == 1)
			return y;
		else
			throw new IllegalArgumentException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer[] coordinates() {
		return new Integer[]{x, y};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EuclideanMetric metric() {
		return metric;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float distance(Coordinates<Integer, Float> other) {
		if(other == null) return null;
		if(other == this) return 0F;
		if(other.getClass() != this.getClass()) return null;
		PixelCoordinates o = (PixelCoordinates) other;
		return metric.distance(this, o);
	}
}