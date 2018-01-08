package elements;

import java.util.Map;

import coordinates.Spherical2DCoordinates;
import directions.Vertex;
import gui.Drawable;
import metric.Metric;
import metric.PolarMetric;
/**
 * Super-interface for all all elements, including nodes, ways, and relations,
 * which can be drawn on a monitor display and which can be traversed as a graph.
 * @author rohithrokkam
 */
public abstract class Element implements Drawable<Spherical2DCoordinates>, Vertex {

	/* The ID of this element. */
	private String id;
	
	/* Tags of this OSMElement. */
	private Map<String, String> tags;

	/* The metric for the space of OSMElements. */
	private static final Metric<Spherical2DCoordinates, Float> metric = PolarMetric.getInstance();
	
	/**
	 * Constructor for an OSMElement with given tags.
	 */
	public Element(String id, Map<String, String> tags) {
		this.id = id;
		this.tags = tags;
	}
	
	/**
	 * Returns the ID of this element.
	 * @return The ID of this element.
	 */
	public String id() {
		return id;
	}
	
	/**
	 * If this element has a tag with the given type, returns the associated
	 * value. Else, returns null.
	 * @return The tag associated with the provided key, or null if no such
	 * 	tag value exists.
	 */
	public String getTag(String key) {
		return tags.get(key);
	}
	
	/**
	 * Returns the metric of this object.
	 * @return The metric of this object.
	 */
	protected Metric<Spherical2DCoordinates, Float> metric() {
		return metric;
	}

	/**
	 * Return true if this element contains the specified other element, 
	 * and false otherwise.
	 * @param other The object which is to be checked for membership in this
	 * 	element.
	 * @return True if this element contains the specified other element, 
	 * and false otherwise.
	 */
	public boolean contains(Element other) {
		return false;
	}
	
	/**
	 * Return a string description of the tags of this element.
	 * @return A string description of the tags of this element.
	 */
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		for(Map.Entry<String, String> tag : tags.entrySet()) {
			description.append(tag.getKey());
			description.append(": ");
			description.append(tag.getValue());
			description.append("\n");
		}
		return description.toString();
	}
	
	/**
	 * Return the distance between this element and another element,
	 * as a vertex. Unsupported by default.
	 * @return The distance between this element and another element,
	 * as a vertex. Unsupported by default.
	 */
	@Override
	public float distance(Vertex v) {
		throw new UnsupportedOperationException();
	}
}