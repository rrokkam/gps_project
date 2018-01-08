package openstreetmap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;
import elements.CompositeElement;
import elements.Element;

/**
 * Represents a sequence of collected members. Relations include
 * other relations, ways, and/or nodes. They can be used, for instance,
 * to represent administrative boundaries.
 */
public class Relation extends CompositeElement {

	/* Members of this relation. */
	private List<Element> members;

	/* The default color for this class. */
	private static final Color COLOR = new Color(190, 62, 130); // moderate purple.
	
	/* The default scale for this class. */
	private static final float SCALE = 2;
	
	/**
	 * Constructs a new Relation.
	 */
	public Relation(String id, Map<String, String> tags, 
			List<Element> members) {
		super(id, tags);
		this.members = members;
	}

	/**
	 * Draws this Relation. It is assumed that relations are nested
	 * properly (i.e. this relation or any of its components cannot 
	 * contain this relation as a subelement).
	 * 
	 * Will not draw any members which are set null.
	 */
	@Override
	public void draw(Graphics g, PixelTransformation<Spherical2DCoordinates> transformation) {
		draw(g, COLOR, SCALE, transformation);
	}

	/**
	 * Draw this relation with the specified color and stroke overriding
	 * the default.
	 */
	@Override
	public void draw(Graphics g, Color c, float scale,
			PixelTransformation<Spherical2DCoordinates> transformation) {
		Color oldColor = g.getColor();
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setColor(c);
		g2.setStroke(new BasicStroke(scale * transformation.changeFromCanonicalScaling()));
		for(Iterator<Element> it = members.iterator(); it.hasNext();) {
			Element next = it.next();
			if(next != null) next.draw(g, transformation);
		}
		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
	}
}