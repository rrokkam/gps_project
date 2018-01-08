package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;
import directions.Vertex;
import openstreetmap.Node;

/**
 * An element which is pointlike - it must have well-defined
 * and singular coordinates.
 * @author rohithrokkam
 */
public abstract class PrimitiveElement extends Element {

	/* The coordinates of this node. */
	protected Spherical2DCoordinates coordinates;

	/**
	 * Make a new abstract node.
	 * @param id The id of the node to create.
	 * @param tags The tags of the node to create.
	 */
	public PrimitiveElement(String id, Map<String, String> tags) {
		super(id, tags);
	}

	/**
	 * Return the coordinates of this point.
	 * @return The coordinates of this point.
	 */
	public Spherical2DCoordinates coordinates() {
		return coordinates;
	}

	/**
	 * Returns true if this object is inside the region. Depending on
	 * how fine the bounds are drawn, this method is not guaranteed to 
	 * return false if this object is outside the given bounds. 
	 * (Due to the Java standard implementation of contains()).
	 */
	@Override
	public boolean isInside(Shape region) {
		return region.contains(coordinates.getLon(), coordinates.getLat());
	}

	/**
	 * Returns the distance between this Node and another Node.
	 * If the object to be compared to is not a valid target of comparison, 
	 * returns Float.POSITIVE_INFINITY.
	 */
	@Override
	public float distance(Vertex v) {
		if(v == null) return Float.POSITIVE_INFINITY;
		if(v == this) return 0F;
		if(v.getClass() != getClass()) return Float.POSITIVE_INFINITY;
		Node n = (Node) v;
		return metric().distance(coordinates(), n.coordinates());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void draw(Graphics g2, 
			PixelTransformation<Spherical2DCoordinates> transformation);

	/**
	 * Draws this composite element. The specified color and stroke
	 * override the ones provided by graphics.
	 */
	@Override
	public abstract void draw(Graphics g, Color c, float scale,
			PixelTransformation<Spherical2DCoordinates> transformation);

	/**
	 * Return a string description of this Node object.
	 * @return A string description of this Node object.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + coordinates.toString();
	}
}