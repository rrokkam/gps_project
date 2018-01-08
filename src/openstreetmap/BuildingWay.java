package openstreetmap;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * A way denoting a building.
 * @author rohithrokkam
 */
public class BuildingWay extends Way {

	/* The default color for this class. */
	private static final Color COLOR = new Color(221, 171, 132); // Light brown.
	
	/* The default scale for this class. */
	private static final float SCALE = 1;
	
	/**
	 * Construct a BuildingWay.
	 */
	public BuildingWay(String id, Map<String, String> tags, List<Node> nodes) {
		super(id, tags, nodes);
	}
	
	/**
	 * Draw this building.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		super.draw(g, COLOR, SCALE, transformation);
	}
}
