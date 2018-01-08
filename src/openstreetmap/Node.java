package openstreetmap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Map;
import coordinates.PixelCoordinates;
import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;
import elements.PrimitiveElement;

/**
 * Represents a pointlike object. Nodes can be used
 * as members of ways, or as markers of specific points
 * of interest.
 * @author rohithrokkam
 */
public class Node extends PrimitiveElement {

	/* The default radius for drawing a Node, in pixels. */
	private static final int DEFAULT_DRAW_RADIUS = 2;
	
	/**
	 * Constructs a new Node given a Point2D location in latitude
	 * and longitude.
	 */
	public Node(String id, Map<String, String> tags, Spherical2DCoordinates coordinates) {
		super(id, tags);
		this.coordinates = coordinates;
	}
	
	/**
	 * Returns the draw radius of this node.
	 * @return The draw radius of this node.
	 */
	protected int radius() {
		return DEFAULT_DRAW_RADIUS;
	}

	/**
	 * Draws a Node. The default implementation is to draw a small
	 * filled circle around the sphericalPoint (whose location in
	 * pixels is determined by the MapScaler).
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		// Do nothing. Don't draw any unhighlighted default nodes.
	}

	/**
	 * Draw a node with the specified color and stroke.
	 */
	@Override
	public void draw(Graphics g, Color c, float scale, 
			PixelTransformation<Spherical2DCoordinates> transformation) {
		Graphics2D g2 = (Graphics2D) g;
		Color oldColor = g2.getColor();
		Stroke oldStroke = g2.getStroke();
		g2.setColor(c);
		float scaleFactor = scale * transformation.changeFromCanonicalScaling();
		scaleFactor = (scaleFactor + 1) / 2;
		g2.setStroke(new BasicStroke(scaleFactor));
		PixelCoordinates pc = transformation.convertTo(coordinates);
		g.fillOval((int) (pc.getX() - (radius() * scaleFactor)/2),
				(int) (pc.getY() - (radius() * scaleFactor)/2),
				(int) (radius() * scaleFactor), (int) (radius() * scaleFactor));
		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
	}
}