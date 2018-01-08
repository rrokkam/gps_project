package openstreetmap;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coordinates.Spherical2DCoordinates;
import data.IteratorGraph;
import data.IteratorMap;
import elements.Element;
import parser.Generator;
import parser.AbstractGenerator;
import parser.Parser;

/**
 * OSM-object creator that converts String inputs from an XMLParser into
 * OSMElements. Maps can be retrieved from the generator with map(). Generators
 * have exact knowledge of the OSM file format.
 * 
 * @author rohithrokkam
 */
public class MapGenerator extends AbstractGenerator<String, Element> implements Generator {

	/* Road elements which have been parsed. */
	private Map<String, Element> elements;

	/* Attributes of the element currently being parsed. */
	private Map<String, String> attributes;

	/* Tags of the element currently being parsed. */
	private Map<String, String> tags;

	/* Ordered constituent elements of the element currently being parsed. */
	private List<Element> members;
	private List<Node> nds;
	
	/* Graph of adjacencies for the map to be returned. */
	private Map<Element, List<Element>> graph;
	
	/* Bounds for this map, in OSM standard minlat-minlon-maxlat-maxlon form. */
	private Rectangle2D bounds;

	/**
	 * Constructs a new OSMGenerator given a parser to use.
	 * @param The parser to use with this map generator.
	 */
	public MapGenerator(Parser parser) {
		super(parser);
		parser.setGenerator(this);
		elements = new HashMap<String, Element>();
		attributes = new HashMap<String, String>();
		tags = new HashMap<String, String>();
		members = new ArrayList<Element>();
		nds = new ArrayList<Node>();
		graph = new HashMap<Element, List<Element>>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IteratorMap<String, Element> map() {
		return new IteratorMap<String, Element>(elements);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle2D bounds() {
		return bounds;
	}
	
	/**
	 * Return a graph representation of the connections between elements
	 * (in this case, entirely nodes) of the provided map.
	 * @return A graph representation of the connections between elements
	 * of the provided map.
	 */
	@Override
	public IteratorGraph<Element> graph() {
		return new IteratorGraph<Element>(graph);
	}

	/**
	 * Method called by the XMLParser when it encounters the beginning of a new
	 * element. Creates a new OSMElement and assigns it attributes.
	 * @param type The type of the object to create.
	 * @param atts Specific information about the instance of the object to create.
	 */
	@Override
	public void startElement(String type, HashMap<String, String> atts) {
		switch (type) {
		// Set attributes for a primitive element.
		case "way":
		case "node":
		case "relation":
		case "bound":
		case "bounds":
			clear(); // Primitives can't be nested, so clear previous info.
			attributes = atts;
			break;
		// Set attributes for a sub-element.
		case "nd":
			String nodeID = "n" + atts.get("ref");
			nds.add((Node) elements.get(nodeID));
			break;
		case "member":
			String memberID = atts.get("type").substring(0, 1) + atts.get("ref");
			members.add(elements.get(memberID));
			break;
		// Set a tag.
		case "tag":
			tags.put(atts.get("k"), atts.get("v"));
			break;
		}
	}
	
	/**
	 * Method called by the XMLParser when it encounters the end of an element.
	 * Adds the finished element to the list of elements this OSMGenerator has
	 * processed.
	 * @param type The type of the object to create.
	 */
	@Override
	public void endElement(String type) {
		String id = attributes.get("id");
		// Add letter to the start of the hash to avoid collisions.
		switch (type) {
		case "node":
			makeNode("n" + id);
			break;
		case "way":
			makeWay("w" + id);
			break;
		case "relation":
			makeRelation("r" + id);
			break;
		case "bound":
		case "bounds":
			makeBounds();
			break;
		} // Else, do nothing.
	}

	/**
	 * Add a node to the element list.
	 */
	private void makeNode(String id) {
		Spherical2DCoordinates sc = new Spherical2DCoordinates(
				Float.valueOf(attributes.get("lon")),
				Float.valueOf(attributes.get("lat")));
		HashMap<String, String> nodeTags = new HashMap<String, String>(tags);
		Node node;
		if(tags.get("place") != null) 
			node = new PlaceNode(id, nodeTags, sc);
		else
			node = new Node(id, nodeTags, sc);
		elements.put(id, node);
		graph.put(node, new ArrayList<Element>(2));
	}

	/**
	 * Add a way to the element list.
	 */
	private void makeWay(String id) {
		Way way;
		HashMap<String, String> wayTags = new HashMap<String, String>(tags);
		ArrayList<Node> nodes = new ArrayList<Node>(nds);
		
		if ("yes".equals(tags.get("building"))) {
			way = new BuildingWay(id, wayTags, nodes);
			elements.put(id, way);
		} else if (tags.get("highway") != null) { // The road is drivable.
			switch (tags.get("highway")) {
			case "primary":
			case "secondary":
				way = new HighWay(id, wayTags, nodes);
				elements.put(id, way);
			case "tertiary":
			case "residential":
				way = new LocalWay(id, wayTags, nodes);
				elements.put(id, way);
			}
		} else {
			// Default case:
			way = new Way(id, wayTags, nodes);
			elements.put(id, way);
		}
		
		boolean oneWay = "yes".equals(wayTags.get("oneway"));
		Element prevVertex = nodes.get(0);
		Element nextVertex;
		for(int i = 0; i + 1 < nodes.size(); i++) {
			nextVertex = nodes.get(i+1);
			graph.get(prevVertex).add(nextVertex);
			if(!oneWay)
				graph.get(nextVertex).add(prevVertex);
			prevVertex = nextVertex;
		}
	}

	/**
	 * Add a relation to the element list.
	 */
	private void makeRelation(String id) {
		Relation relation = new Relation(id, new HashMap<String, String>(tags),
				new ArrayList<Element>(members));
		elements.put(id, relation);
		// Don't add relations to graphs.
	}

	/**
	 * Add bounds to the element list.
	 */
	private void makeBounds() {
		float minlat = Float.valueOf(attributes.get("minlat"));
		float minlon = Float.valueOf(attributes.get("minlon"));
		float maxlat = Float.valueOf(attributes.get("maxlat"));
		float maxlon = Float.valueOf(attributes.get("maxlon"));
		bounds = new Rectangle2D.Float(minlon, minlat, maxlon - minlon, maxlat - minlat);
	}

	/**
	 * Clears data for the next element to be generated.
	 */
	private void clear() {
		attributes.clear();
		tags.clear();
		nds.clear();
		members.clear();
	}
}