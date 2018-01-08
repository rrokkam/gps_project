
package openstreetmap;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * A way denoting a local road.
 * @author rohithrokkam
 */
public class LocalWay extends Way {

	/* The default color for this class. */
	private static final Color COLOR = new Color(107, 129, 140); // Light blue
	
	/* The default scale for this class. */
	private static final float SCALE = 2;
	
	/**
	 * Construct a local road.
	 */
	public LocalWay(String id, Map<String, String> tags, List<Node> nodes) {
		super(id, tags, nodes);
	}

	/**
	 * Draw this LocalWay.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		super.draw(g, COLOR, SCALE, transformation);
	}

}
