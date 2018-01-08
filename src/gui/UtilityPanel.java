package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import openstreetmap.Node;
import state.MapState;
import state.StateListener;

/**
 * The utility panel for the OSM Map application. The text panel
 * includes a search feature and lists directions between locations
 * specified by a client.
 * @author rohithrokkam
 */
@SuppressWarnings("serial")
public class UtilityPanel extends JPanel implements StateListener {
	
	/* The tab of the text panel which handles displaying directions. */
	private DirectionsPanel directionsPanel;
	
	/* Labels for the start point for direction searching. */
	private JLabel startLabel;
	
	/* Labels for the end point for direction searching. */
	private JLabel endLabel;
	
	/* String that indicates that no start location has been set. */
	private final String NO_START_SET = "No start set";
	
	/* String that indicates that no end location has been set. */
	private final String NO_END_SET = "No end set";

	/*  The state information of the map to be drawn. */
	private MapState state;
	
	/**
	 * Constructs a new TextPanel.
	 */
	public UtilityPanel(Dimension size, MapState state) {
		this.state = state;
		setPreferredSize(size);
		setLayout(new GridLayout(0, 1));

		JButton getDirectionsButton = new JButton();
		getDirectionsButton.setText("Get Directions");
		getDirectionsButton.setPreferredSize(new Dimension(getWidth(), 20));
		getDirectionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((state.getStart() != null) && (state.getEnd() != null) ) 
					state.setNeedDirections(true);
			}
		});
		add(getDirectionsButton);
		
		JButton driveThereButton = new JButton();
		driveThereButton.setText("Toggle Drive There");
		driveThereButton.setPreferredSize(new Dimension(getWidth(), 20));
		driveThereButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(state.getDrivingThere() == true)
					state.setDrivingThere(false);
				else
					state.setDrivingThere(true);
			}
		});
		add(driveThereButton);

		JButton resetButton = new JButton();
		resetButton.setText("Reset");
		resetButton.setPreferredSize(new Dimension(getWidth(), 20));
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state.clear();
			}
		});
		add(resetButton);

		
		startLabel = new JLabel();
		startLabel.setText(NO_START_SET);
		startLabel.setPreferredSize(new Dimension(getWidth(), 50));
		add(startLabel);
		
		endLabel = new JLabel();
		endLabel.setText(NO_END_SET);
		endLabel.setPreferredSize(new Dimension(getWidth(), 50));
		add(endLabel);
		
		directionsPanel = new DirectionsPanel();
		directionsPanel.setPreferredSize(new Dimension(getWidth(), 450));
		add(directionsPanel);
	}

	/**
	 * Called by the state object when its properties have been updated.
	 */
	@Override
	public void stateUpdated() {
		Node start = state.getStart();
		Node end = state.getEnd();
		if(start == null)
			startLabel.setText(NO_START_SET);
		else
			startLabel.setText(start.toString());
		if(end == null)
			endLabel.setText(NO_END_SET);
		else
			endLabel.setText(end.toString());
		
		//request directions non-bad?
		directionsPanel.displayDirections(state.getDirections());
		revalidate();
	}
}