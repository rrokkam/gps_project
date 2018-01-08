package gui;

import javax.swing.JScrollPane;

import directions.Directions;

/**
 * Stub class for a mini-panel which will contain labels for
 * starting and ending labels for getting directions.
 * @author rohithrokkam
 */
@SuppressWarnings("serial")
public class DirectionsPanel extends JScrollPane {

	/**
	 * Display the directions in the directions panel.
	 * @param directions The directions to display.
	 */
	public void displayDirections(Directions directions) {
		if(directions == null) {
			removeAll();
			revalidate();
			repaint();
		} else {
			// Make a bunch of JLabels.
		}
	}
}
