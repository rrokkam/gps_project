package openstreetmap;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * A way denoting a highway.
 * @author rohithrokkam
 */
public class HighWay extends Way {

	/* The default color for this class. */
	private static final Color COLOR = new Color(8, 65, 92); // Darker blue
	
	/* The default scale for this class. */
	private static final float SCALE = 2;
	
	/**
	 * Create a highway.
	 */
	public HighWay(String id, Map<String, String> tags, List<Node> nodes) {
		super(id, tags, nodes);
	}
	
	/**
	 * Draw this highway.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		super.draw(g, COLOR, SCALE, transformation);
	}

}
