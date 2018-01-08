package openstreetmap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import coordinates.PixelCoordinates;
import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;
import elements.CompositeElement;
import openstreetmap.Node;

/**
 * Represents a sequence of connected Nodes. Ways can be roads, outlines of
 * structures, or other groups of nodes of interest.
 */
public class Way extends CompositeElement {

	/* IDs of nodes that comprise this Way. */
	private List<Node> nodes;
	
	/* The default color for this class. */
	private static final Color COLOR = new Color(107, 129, 140); // Light blue
	
	/* The default stroke for this class. */
	private static final float SCALE = 1;

	/**
	 * Constructs a new Way. The provided map must be guaranteed to have a
	 * predictable iteration order.
	 */
	public Way(String id, Map<String, String> tags, List<Node> nodes) {
		super(id, tags);
		this.nodes = nodes;
	}

	/**
	 * Draws this Way. The default implementation is to draw lines between each
	 * of the nodes.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		draw(g, COLOR, SCALE, transformation);
	}

	/**
	 * Draw this way using the specified color and stroke. The Graphics context is
	 * reset to its original state after this method is done running.
	 */
	@Override
	public void draw(Graphics g, Color c, float scale, 
			PixelTransformation<Spherical2DCoordinates> transformation) {
		Iterator<Node> it = nodes.iterator();
		GeneralPath path = new GeneralPath();
		
		Spherical2DCoordinates c1 = it.next().coordinates();
		PixelCoordinates p1 = transformation.convertTo(c1);
		path.moveTo(p1.getX(), p1.getY());
		while(it.hasNext()) {
			Spherical2DCoordinates c2 = it.next().coordinates();
			PixelCoordinates p2 = transformation.convertTo(c2);
			path.lineTo(p2.getX(), p2.getY());
		}
		Graphics2D g2 = (Graphics2D) g;
		Color oldColor = g2.getColor();
		Stroke oldStroke = g2.getStroke();
		g2.setColor(c);
		g2.setStroke(new BasicStroke(scale * transformation.changeFromCanonicalScaling(),
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(path);
		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
	}
}