package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import coordinates.Coordinates;
import coordinates.PixelTransformation;
import coordinates.Spherical2DCoordinates;

/**
 * Interfaced implemented by objects which know how to
 * draw themselves.
 * @author rohithrokkam
 */
public interface Drawable<C extends Coordinates<?,?>> {
	
	/**
	 * Draws this object.
	 * @param g2 Graphics context
	 * @param transformation Scaler to pixel coordinates.
	 */
	public void draw(Graphics g, PixelTransformation<C> transformation);
	
	/**
	 * Draws this object with the specified color and scale factor.
	 * @param g2 Graphics context
	 * @param transformation Scaler to pixel coordinates.
	 * @param c The color to draw this object with.
	 * @param scale The scale factor to draw this object at.
	 */
	public abstract void draw(Graphics g, Color c, float scale,
			PixelTransformation<Spherical2DCoordinates> transformation);
	/**
	 * Return true if the given Drawable element is inside the provided region,
	 * and false otherwise.
	 * @param region The region to consider.
	 * @return True if the given Drawable element is inside the provided region,
	 * and false otherwise.
	 */
	public boolean isInside(Shape region);
	
	/**
	 * Return the coordinates of this object if it is a point object.
	 * Otherwise, return null.
	 */
	public C coordinates();
}
