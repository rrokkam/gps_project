package parser;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import data.IteratorGraph;
import data.IteratorMap;

/**
 * Can generate files and return maps corresponding to the files generated.
 * @author rohithrokkam
 * @param <E> The type of element in the map.
 */
public abstract class AbstractGenerator<K, V> {

	/* Parser for this generator. */
	private Parser parser;
	
	public AbstractGenerator(Parser parser) {
		this.parser = parser;
	}
	
	/**
	 * Returns the map corresponding to the elements that have been parsed by
	 * this OSMGenerator since its creation.
	 * @return The map corresponding to the elements that have been parsed by
	 * this OSMGenerator since its creation.
	 */
	public abstract IteratorMap<K, V> map();
	
	/**
	 * Returns the bounds of the map found by getting map().
	 * @return The bounds of the map found by getting map()
	 */
	public abstract Rectangle2D bounds();
	
	/**
	 * Optional operation which returns a graph view of the items in
	 * the map provided by this generator.
	 */
	public IteratorGraph<?> graph() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Attempts to generate a map from the file given.
	 */
	public void generate(File file) {
		try {
			parser.parse(file);
		} catch (IOException e) {
			// Let it slide for now...
			System.err.println("OSMGenerator error: " + file.getName());
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts to generate a map from each of the files given.
	 * 
	 * @param files
	 *            The files to be read.
	 */
	public void generate(File[] files) {
		for (File f : files) {
			generate(f);
		}
	}

	/**
	 * Attempts to generate a map using the filename given.
	 */
	public void generate(String filename) {
		File file = new File(filename);
		generate(file);
	}

	/**
	 * Attempts to generate a map using of the filenames given.
	 * 
	 * @param filenames
	 *            The filenames whose contents are to be read.
	 */
	public void generate(String[] filenames) {
		int length = filenames.length;
		for (int i = 0; i < length; i++) {
			generate(filenames[i]);
		}
	}
}