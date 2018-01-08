package metric;

import coordinates.PixelCoordinates;

/**
 * A utility class which provides the surface distance between two points on the
 * Earth's surface, given their latitude and longitude coordinates.
 * @author rohithrokkam
 */
public class EuclideanMetric implements Metric<PixelCoordinates, Float> {

	/* The singleton instance of a Haversine Metric. */
	private static EuclideanMetric instance = null;

	/**
	 * Stop clients from directly creating an instance of a Haversine Metric.
	 */
	private EuclideanMetric() {
		// Do nothing
	}

	/**
	 * Returns the EuclideanMetric object.
	 * @return The Euclidean metric.
	 */
	public static EuclideanMetric getInstance() {
		if (instance == null) {
			instance = new EuclideanMetric();
		}
		return instance;
	}

	/**
	 * Return the Euclidean distance between two PixelCoordinates.
	 * @return The Euclidean distance between two PixelCoordinates.
	 */
	@Override
	public Float distance(PixelCoordinates first, PixelCoordinates second) {
		float distanceSq = distanceSq(first.getX(), first.getY(), 
				second.getX(), second.getY());
		return (float) Math.sqrt(distanceSq);
	}
	
	/**
	 * Find the square of the distance between the given coordinates.
	 * @return The square of the distance between the given coordinates.
	 */
	private float distanceSq(float x1, float y1, float x2, float y2) {
		y1 -= y2;
		x1 -= x2;
		y1 *= y1;
		x1 *= x1;
		return x1 + y1;
	}
}