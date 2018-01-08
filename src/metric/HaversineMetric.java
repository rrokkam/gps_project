package metric;

import coordinates.Spherical2DCoordinates;

/**
 * A utility class which provides the surface distance between two points on the
 * Earth's surface, given their latitude and longitude coordinates.
 * The HaversineMetric distance is exact (within floating-point position), except
 * that it does not take into account the altitude of points, instead using the
 * mean diameter of Earth as the altitude of all points.
 * @author rohithrokkam
 */
public class HaversineMetric implements Metric<Spherical2DCoordinates, Float> {

	/* The average radius of the earth in kilometers. */
	private static final int EARTH_RADIUS = 6371;

	/* The singleton instance of a Haversine Metric. */
	private static HaversineMetric instance = null;

	/**
	 * Stop clients from directly creating an instance of a Haversine Metric.
	 */
	private HaversineMetric() {
		// Do nothing.
	}

	/**
	 * Returns the HaversineMetric object.
	 * @return The HaversineMetric object.
	 */
	public static HaversineMetric getInstance() {
		if (instance == null) {
			instance = new HaversineMetric();
		}
		return instance;
	}
	
	/**
	 * Return the surface distance in kilometers between two points on 
	 * the Earth's surface, given their latitude and longitude coordinates.
	 * @return The surface distance in kilometers between two points on 
	 * the Earth's surface. 
	 */
	@Override
	public Float distance(Spherical2DCoordinates first, Spherical2DCoordinates second) {
		return distance(first.getLon(), first.getLat(), second.getLon(), second.getLat());
	}
	
	/**
	 * Helper method for the primary distance method. Compute the distance
	 * in float coordinates.
	 * @return The surface distance in kilometers between two points on the
	 * Earth's surface.
	 */
	private float distance(float lon1, float lat1, float lon2, float lat2) {
		float halfLon = radians(lon2 - lon1)/2;
		float halfLat = radians(lat2 - lat1)/2;
		double first = Math.pow(Math.sin(halfLat),2) + Math.cos(radians(lat1))
				* Math.cos(radians(lat2)) * Math.pow(Math.sin(halfLon), 2);
		double second = 2 * Math.atan2(Math.sqrt(first), Math.sqrt(1 - first));
		float distance = (float) (EARTH_RADIUS * second);
		return distance;
	}

	/**
	 * Convert an angle in degrees to radians.
	 * @param An angle in degrees.
	 * @return The provided angle in radians.
	 */
	private float radians(float degrees) {
		return (float) (degrees * Math.PI / 180);
	}
}