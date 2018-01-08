package main;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * A class which applications will extend to run in a frame.
 * 
 * Users should subclass this class and override the getTitle and getDimension
 * methods to set their own frame title and frame dimensions.
 * 
 * @author rohithrokkam
 */
@SuppressWarnings("serial")
public abstract class FramedApplication extends JFrame {

	/* Default width for the application frame. */
	private static final int DEFAULT_WIDTH = 800;

	/* Default height for the application frame. */
	private static final int DEFAULT_HEIGHT = 600;

	/* Default title for the application frame. */
	private static final String DEFAULT_TITLE = "Unnamed Application";

	/**
	 * Constructs a new Application. The default functionality is to construct a
	 * frame with default dimensions and title, and then to call the
	 * initialize() method of this subclass.
	 */
	protected FramedApplication(String[] args) {
		setSize(getDimension());
		setTitle(getTitle());
		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Full-screen code:
		// setSize(Toolkit.getDefaultToolkit().getScreenSize());
		// setUndecorated(true);

		initialize(args);
		revalidate();
		pack();
		setVisible(true);
	}

	/**
	 * Returns the title of this application. Implementations of this class
	 * should override this method to return the title of their application.
	 * @return The title of this application.
	 */
	@Override
	public String getTitle() {
		return DEFAULT_TITLE;
	}

	/**
	 * Returns the dimensions of this application. Implementations of this class
	 * should override this method to return the dimensions of their
	 * application.
	 * @return The dimensions of this application.
	 */
	protected Dimension getDimension() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Calls the initialize method of the implementing class. This method should
	 * be overridden to set up the client's specific application (ex. by adding
	 * panels to the content pane).
	 */
	abstract protected void initialize(String[] args);
}