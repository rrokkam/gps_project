package elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.util.Map;

import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * An element which is composed of multiple primitive elements
 * and composite elements.
 * @author rohithrokkam
 */
public abstract class CompositeElement extends Element {

	/**
	 * Construct a new Composite element.
	 * @param id The ID of the new element.
	 * @param tags The tags of the element.
	 */
	public CompositeElement(String id, Map<String, String> tags) {
		super(id, tags);
	}

	/**
	 * Returns true if any part of this element is contained within
	 * the region specified in latitude and longitude. Returns
	 * false otherwise. Default implementation is to throw an
	 * UnsupportedOperationException.
	 * @return True if this element is at least partially contained
	 * 	in the specified region, and false otherwise.
	 */
	@Override
	public boolean isInside(Shape region) {
		return false;
	}

	/**
	 * Returns true if this element contains the specified member
	 * element, and false otherwise.
	 * @return True if this element contains the specified member
	 * element, and false otherwise.
	 */
	@Override
	public boolean contains(Element other) {
		return false;
	}

	/**
	 * Return null, since relations are not point objects.
	 * @return Null, since relations are not point objects.
	 */
	@Override
	public Spherical2DCoordinates coordinates() {
		return null;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public abstract void draw(Graphics g, 
			PixelTransformation<Spherical2DCoordinates> transformation);
	
	/**
	 * Draws this composite element. The specified color and stroke
	 * override the ones provided by graphics.
	 */
	@Override
	public abstract void draw(Graphics g, Color c, float scale,
			PixelTransformation<Spherical2DCoordinates> transformation);
}