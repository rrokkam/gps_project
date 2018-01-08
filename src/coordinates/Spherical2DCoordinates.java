package coordinates;

import metric.Metric;
import metric.PolarMetric;

/**
 * Members of this class represent locations on the surface of a sphere.
 * @author rohithrokkam
 */
public class Spherical2DCoordinates implements Coordinates<Float, Float> {

	/* The longitude of this element. */
	private float lon;

	/* The latitude of this element. */
	private float lat;
	
	/* The metric for the space of spherical 2D coordinates. */
	private static final Metric<Spherical2DCoordinates, Float> metric = 
			PolarMetric.getInstance();
	
	/**
	 * Construct a new set of spherical 2D coordinates.
	 */
	public Spherical2DCoordinates(float lon, float lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	/**
	 * Returns the longitude of these coordinates.
	 * @return The longitude of these coordinates.
	 */
	public float getLon() {
		return lon;
	}
	
	/**
	 * Returns the latitude of these coordinates.
	 * @return The latitude of these coordinates.
	 */
	public float getLat() {
		return lat;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float coordinate(int index) {
		if (index == 0)
			return lon;
		else if (index == 1)
			return lat;
		else
			throw new IllegalArgumentException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float[] coordinates() {
		return new Float[]{lon, lat};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Metric<Spherical2DCoordinates, Float> metric() {
		return metric;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Float distance(Coordinates<Float, Float> other) {
		if(other == null) return null;
		if(other == this) return 0F;
		if(other.getClass() != this.getClass()) return null;
		Spherical2DCoordinates o = (Spherical2DCoordinates) other;
		return metric.distance(this, o);
	}

	/**
	 * Print a string representation of these coordinates.
	 */
	@Override
	public String toString() {
		return "lon=" + lon + "; lat=" + lat;
	}
}