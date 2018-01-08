package openstreetmap;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * A node denoting a place of interest.
 * @author rohithrokkam
 */
public class PlaceNode extends Node {

	/* The default color for this class. */
	private static final Color COLOR = new Color(204, 91, 104); // Reddish
	
	/* The default stroke for this class. */
	private static final float SCALE = 3;

	/**
	 * Make a new PlaceNode.
	 */
	public PlaceNode(String id, Map<String, String> tags, Spherical2DCoordinates coordinates) {
		super(id, tags, coordinates);
	}

	/**
	 * Draw this PlaceNode.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		super.draw(g, COLOR, SCALE, transformation);
	}
	
	/**
	 * Draw this PlaceNode.
	 */
	@Override
	public void draw(Graphics g, Color c, float scale, 
			PixelTransformation<Spherical2DCoordinates> transformation) {
		super.draw(g, c, scale, transformation);
	}
}
