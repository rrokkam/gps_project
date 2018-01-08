package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import coordinates.PixelCoordinates;
import coordinates.Spherical2DCoordinates;
import data.IteratorMap;
import directions.Directions;
import directions.Vertex;
import state.MapState;
import state.StateListener;
import elements.Element;
import openstreetmap.Node;
import openstreetmap.Way;

/**
 * A Map panel which displays the contents of an 
 * IteratorMap of Elements in spherical coordinates.
 * @rohithrokkam
 */
@SuppressWarnings("serial")
public class GPSPanel extends JPanel implements StateListener {

	/* The map whose contents are to be displayed on this panel. */
	private IteratorMap<String, Element> map;

	/* Scaling strategy used to determine map element locations. */
	private MapScaler scaler;

	/*  The state information of the map to be drawn. */
	private MapState state;
	
	/* The last location at which the user pressed on this panel, 
	 * in pixel coordinates, negated. */
	private PixelCoordinates pressCoords;

	/* A list of elements to highlight. */
	private Element highlightedElement;
	
	/**
	 * Constructs a new MapPanel given a StreetMap, the size to display in, and
	 * the map bounds.
	 */
	public GPSPanel(IteratorMap<String, Element> map, 
			Rectangle2D mapBounds, Dimension panelBounds, MapState state) {
		this.map = map;
		this.state = state;
		
		setPreferredSize(panelBounds);
		setBackground(new Color(255, 242, 253)); // Off-pink
		
		Spherical2DCoordinates sc = new Spherical2DCoordinates(
				(float) mapBounds.getCenterX(), (float) mapBounds.getCenterY());
		PixelCoordinates pc = new PixelCoordinates(
				(int) panelBounds.getWidth()/2, (int) panelBounds.getHeight()/2);
		scaler = new MapScaler(pc, sc);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pressCoords = new PixelCoordinates(e.getX(), e.getY());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if(!state.getDrivingThere()) {
					PixelCoordinates pc = new PixelCoordinates(e.getX(), e.getY());
					if(SwingUtilities.isRightMouseButton(e)) {
						state.setEnd((Node) state.getObjectAt(scaler.convertFrom(pc)));
						System.out.println("set end");
					} else if (SwingUtilities.isLeftMouseButton(e)) {
						state.setStart((Node) state.getObjectAt(scaler.convertFrom(pc)));
					}
				}
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!state.getDrivingThere()) { // can't pan when driving.
				PixelCoordinates newCoords = 
						new PixelCoordinates(e.getX() - pressCoords.getX(), 
											e.getY() -pressCoords.getY());
				scaler = scaler.translateBy(newCoords);
				pressCoords = new PixelCoordinates(e.getX(), e.getY());
				repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(!state.getDrivingThere()) {
					Point2D p = e.getPoint();
					PixelCoordinates pc = new PixelCoordinates(
							(int) p.getX(),(int) p.getY());
					highlightedElement = state.getObjectAt(scaler.convertFrom(pc));
					repaint();
				}
			}
		});

		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(!state.getDrivingThere()) {
					int scrollAmount = e.getWheelRotation();
				if (scrollAmount < 0)
					scaler = scaler.scaleDown();
				else
					scaler = scaler.scaleUp();
					repaint();
				}
			}
		});

//		addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				Rectangle newBounds = getBounds();
//				//scaler.pixelCenterX = newBounds.getCenterX();
//				//scaler.pixelCenterY = newBounds.getCenterY();
//				repaint();
//			}
//		});
	}
	
	/**
	 * Called by a state object observing this view when it is modified.
	 */
	@Override
	public void stateUpdated() {
		if(state.getDrivingThere() && (state.getLastCoordinates() != null)) {
			// Center the screen on the user's location.
			scaler = scaler.setCenter(state.getLastCoordinates());
			scaler = scaler.scaleToZoom(10F);
			highlightedElement = null;
		}
		repaint();
	}

	/**
	 * Paints this Map Panel. Paints everything on the map, for now.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Iterator<Element> it = map.iterator(); it.hasNext();)
			it.next().draw(g, scaler);
		if(highlightedElement != null) {
			highlightedElement.draw(g, Color.PINK, 5F, scaler);
		}
		// Draw certain objects specially as specified by the state.
		drawState(g);
	}

	/**
	 * Draw objects marked by the state as special (start point, end point,
	 * computed route, etc).
	 */
	private void drawState(Graphics g) {
		Element start = state.getStart();
		Element end = state.getEnd();
		Directions directions = state.getDirections();
		Graphics2D g2 = (Graphics2D) g;
		if(start != null)
			start.draw(g2, Color.GREEN, 5, scaler);
		if(end != null)
			end.draw(g2, Color.RED, 5, scaler);
		// Unfortunately, some casting. Not sure how to fix this 
		// without parameterizing everything in the directions package
		// to something like <N extends Vertex> which seems a little excessive.
		if((directions != null) && (!Directions.NO_DIRECTIONS.equals(directions))) {
			List<Vertex> vList = directions.asList();
			List<Node> nList = new ArrayList<Node>();
			for(int i = 0; i < vList.size(); i++)
				nList.add((Node) vList.get(i));
			Way directionsWay = new Way(null, null, nList);
			directionsWay.draw(g2, Color.CYAN, 3, scaler); // draw at 5 times the normal
														   // thickness
		}
		if(state.getDrivingThere() && (state.getLastCoordinates() != null)) {
			// Draw a small square at the user's location.
			Spherical2DCoordinates userSC = state.getLastCoordinates();
			PixelCoordinates userPC = scaler.convertTo(userSC);
			Color c = g.getColor();
			g.setColor(Color.YELLOW);
			g.fillRect(userPC.getX() - 2, userPC.getY() - 2, 4, 4);
			g.setColor(c);
		}
	}
}