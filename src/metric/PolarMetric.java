package metric;

import coordinates.Spherical2DCoordinates;

/**
 * An approximation metric which is less computationally expensive
 * than the Haversine metric but which fulfills a similar role.
 * @author rohithrokkam
 */
public class PolarMetric implements Metric<Spherical2DCoordinates, Float> {
	
	/* The average radius of the earth in kilometers. */
	private static final int EARTH_RADIUS = 6371;

	/* The singleton instance of a Polar Metric. */
	private static PolarMetric instance = null;
	
	/**
	 * Stop clients from directly creating an instance of a Polar Metric.
	 */
	private PolarMetric() {
		// Do nothing.
	}
	
	/**
	 * Returns the PolarMetric object.
	 */
	public static PolarMetric getInstance() {
		if (instance == null) {
			instance = new PolarMetric();
		}
		return instance;
	}
	
	/**
	 * Return approximate surface distance in kilometers between two points on 
	 * the Earth's surface, given their latitude and longitude coordinates.
	 * @return Approximate surface distance in kilometers between two points on 
	 * the Earth's surface
	 */
	@Override
	public Float distance(Spherical2DCoordinates first, Spherical2DCoordinates second) {
		double deltaLatSq = Math.pow(radians(first.getLat() - second.getLat()), 2);
		double deltaLon = radians(first.getLon() - second.getLon());
		double latitudeAdjust = Math.cos((first.getLat() + second.getLat()/2));
		double deltaLonSq = Math.pow(deltaLon * latitudeAdjust, 2);
		float D = (float) Math.sqrt(deltaLatSq + deltaLonSq);
		return D * EARTH_RADIUS;
	}
	
	/**
	 * Convert an angle in degrees to radians.
	 */
	private float radians(float degrees) {
		return (float) (degrees * Math.PI / 180);
	}
}
