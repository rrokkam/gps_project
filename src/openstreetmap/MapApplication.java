package openstreetmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import com.starkeffect.highway.GPSDevice;

import directions.Djikstra;
import gui.GPSPanel;
import gui.UtilityPanel;
import main.FramedApplication;
import parser.XMLParser;
import state.DirectionsGenerator;
import state.MapState;
import state.OnTrackChecker;

/**
 * The main application class for the OSM map project.
 * @author rohithrokkam
 */
@SuppressWarnings("serial")
public class MapApplication extends FramedApplication {

	/* Frame width and height. */
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	
	/**
	 * Constructs a Map Application given the
	 * @param args Program arguments to be passed to the initialize
	 * method.
	 */
	private MapApplication(String[] args) {
		super(args);
	}

	/**
	 * Add map-related functionality to the frame.
	 * @param args The arguments given in the main() method.
	 * @param contentPane the content pane of the application frame.
	 */
	@Override
	protected void initialize(String[] args) {
		String fileName = args[0];
		XMLParser p = new XMLParser();
		MapGenerator g = new MapGenerator(new XMLParser());
		p.setGenerator(g);
		g.generate(fileName);
		
		MapState state = new MapState(g.map());
		
		Dimension mapPanelSize = new Dimension(FRAME_HEIGHT, FRAME_HEIGHT);
		GPSPanel mapPanel = new GPSPanel(g.map(), g.bounds(), mapPanelSize, state);
		

		Dimension utilityPanelSize = new Dimension(
				FRAME_WIDTH - FRAME_HEIGHT, FRAME_HEIGHT);
		UtilityPanel utilityPanel = new UtilityPanel(utilityPanelSize, state);
		
		DirectionsGenerator directionsGenerator = 
				new DirectionsGenerator(new Djikstra(g.graph()), state);
		OnTrackChecker onTrackChecker = new OnTrackChecker(state);
		
		GPSDevice gps = new GPSDevice(fileName);
		gps.addGPSListener(state);
		state.register(mapPanel);
		state.register(utilityPanel);
		state.register(directionsGenerator);
		
//		UNCOMMENT THIS LINE TO ENABLE TRACK CHECKING
//		state.register(onTrackChecker);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mapPanel, BorderLayout.CENTER);
		getContentPane().add(utilityPanel, BorderLayout.WEST);
	}
	
	/**
	 * Returns the title of the map application.
	 */
	@Override
	public String getTitle() {
		return "rr-mapproject";
	}
	
	/**
	 * Returns the dimensions of the map application frame.
	 */
	@Override
	public Dimension getDimension() {
		return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	/**
	 * Launches the Open Street Map Application. The first argument in the 
	 * args[] array is the name of the file which is to be parsed and viewed.
	 */
	public static void main(String[] args) {
		new MapApplication(args);
	}
}