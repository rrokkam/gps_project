package gui;

import coordinates.PixelCoordinates;
import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * A scaler which can convert between pixel and spherical coordinates.
 * The fields pcX, pcY, scLon, and scLat are stored to save on method calls
 * to the pixel and spherical coordinates objects.
 */
public class MapScaler implements PixelTransformation<Spherical2DCoordinates> {

	/* Higher numbers increase sensitivity. Set greater than 1.0 */
	private static final float ZOOM_SENSITIVITY = 1.1F;

	/* Number of pixels per degree latitude. */
	private static final int PIXELS_PER_DEGREE_LAT = 6000;
	
	/* The current zoom factor of the scaler. */
	private float zoomFactor = 1.0F;

	/* The pixel center of the display, in x coordinates. */
	private int pcX;
	/* The pixel center of the display, in y coordinates. */
	private int pcY;
	/* The pixel center of the display in coordinate form.*/
	private PixelCoordinates pc;
	
	/* The spherical center of the display, in longitude. */
	private float scLon;
	/* The spherical center of the display, in latitude. */
	private float scLat;
	/* The pixel center of the display in coordinate form.*/
	private Spherical2DCoordinates sc;
	/* The cosine of the latitude of the spherical center, for computations. */
	//private float cos;

	/**
	 * Constructs a MapScaler given a point in both spherical and pixel
	 * coordinates.
	 */
	public MapScaler(PixelCoordinates x, Spherical2DCoordinates y) {
		sc = y;
		pc = x;
		scLon = y.getLon();
		scLat = y.getLat();
		pcX = x.getX();
		pcY = x.getY();
		//cos = (float) Math.cos(Math.PI * scLat / 180);
	}
	
	/**
	 * Constructs a MapScaler given a point in both spherical and pixel
	 * coordinates, and a zoom factor.
	 */
	public MapScaler(PixelCoordinates x, Spherical2DCoordinates y, float zoomFactor) {
		sc = y;
		pc = x;
		scLon = y.getLon();
		scLat = y.getLat();
		pcX = x.getX();
		pcY = x.getY();
		//cos = (float) Math.cos(scLat);
		this.zoomFactor = zoomFactor;
	}
	
	/**
	 * Convert a point in pixel coordinates to spherical coordinates.
	 */
	@Override
	public Spherical2DCoordinates convertFrom(PixelCoordinates y) {
		return new Spherical2DCoordinates(
				(y.getX() - pcX) / (zoomFactor * PIXELS_PER_DEGREE_LAT /* * cos*/) + scLon,
				(y.getY() - pcY) / (zoomFactor * PIXELS_PER_DEGREE_LAT) * -1 + scLat
				);
	}

	/**
	 * Convert a point in spherical coordinates to pixel coordinates.
	 */
	@Override
	public PixelCoordinates convertTo(Spherical2DCoordinates x) {
		return new PixelCoordinates(
				(int) (zoomFactor * PIXELS_PER_DEGREE_LAT /** cos */* -1 * (scLon - x.getLon()) + pcX),
				(int) (zoomFactor * PIXELS_PER_DEGREE_LAT * (scLat - x.getLat()) + pcY)
				);
	}
	
	/**
	 * Return a new MapScaler which will transform absstract coordinates 
	 * to values different from this MapScaler by the specified amount in
	 * pixel coordinates.
	 */
	public MapScaler translateBy(PixelCoordinates amount) {
		Spherical2DCoordinates newSC = new Spherical2DCoordinates(
				sc.getLon() - amount.getX()/(zoomFactor*PIXELS_PER_DEGREE_LAT),
				sc.getLat() + amount.getY()/(zoomFactor*PIXELS_PER_DEGREE_LAT));
		return new MapScaler(pc, newSC, zoomFactor);
	}
	
	/**
	 * Return a new MapScaler which will transform pixel coordinates 
	 * to values different from this MapScaler by the specified amount in
	 * spherical coordinates.
	 */
	public MapScaler translateBy(Spherical2DCoordinates amount) {
		Spherical2DCoordinates newSC = new Spherical2DCoordinates(
				scLon + amount.getLon(), pcY + amount.getLat());
		return new MapScaler(pc, newSC, zoomFactor);
	}
	
	/**
	 * Return a new MapScaler which will have the specified spherical
	 * coordinates at the center of the screen.
	 */
	public MapScaler setCenter(Spherical2DCoordinates newCenter) {
		return new MapScaler(pc, newCenter, zoomFactor);
	}
	
	/**
	 * Return a new MapScaler which will transform abstract coordinates 
	 * to pixel values closer to one another.
	 */
	public MapScaler scaleDown() {
		return new MapScaler(pc, sc, zoomFactor * ZOOM_SENSITIVITY);
	}
	
	/**
	 * Return a new MapScaler which will transform abstract coordinates 
	 * to pixel values further from one another.
	 */
	public MapScaler scaleUp() {
		return new MapScaler(pc, sc, zoomFactor / ZOOM_SENSITIVITY);
	}
	
	/**
	 * Return a new MapScaler with the specified degree of zoom.
	 * @param newZoom The zoom factor of the new scaler. This value should
	 * 	always be set greater than 1.
	 */
	public MapScaler scaleToZoom(float newZoom) {
		return new MapScaler(pc, sc, newZoom);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public float changeFromCanonicalScaling() {
		return zoomFactor;
	}
}