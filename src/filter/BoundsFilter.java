package filter;

import java.awt.Shape;

import elements.Element;

/**
 * A bounds-based filter for OSMElements that can determine,
 * given a bounding shape, whether an element is inside the
 * shape or not. This can be used, for instance, to avoid
 * rendering elements on a map which will not fall in the 
 * visible display when coordinates are converted to pixels.
 * @author rohithrokkam
 */
public class BoundsFilter implements Filter<Element> {

	/* The bounding shape for this filter. */
	private final Shape bounds;
	
	/**
	 * Construct a new bounds-based filter for OSMElements
	 * given a bounding shape.
	 * @param bounds The bounding shape for this filter.
	 */
	public BoundsFilter(final Shape bounds) {
		this.bounds = bounds;
	}
	
	/**
	 * Return the bounds of this shape.
	 * @return The bounds of this shape.
	 */
	public Shape getBounds() {
		return bounds;
	}
	
	/**
	 * Return true if the object is contained in the bounds,
	 * and false otherwise.
	 * @return true if the object is contained in the bounds,
	 * and false otherwise.
	 */
	@Override
	public boolean satisfiesCondition(Element e) {
		return e.isInside(bounds);
	}
}