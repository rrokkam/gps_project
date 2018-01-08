package parser;

import java.io.File;
import java.io.IOException;

/**
 * Objects that implement this interface can parse files.
 */
public abstract class Parser {
	
	/* The generator for this parser. */
	private Generator generator;
	
	/**
	 * Sets the generator for this parser.
	 */
	public void setGenerator(final Generator generator) {
		this.generator = generator;
	}
	
	/**
	 * Gets a reference to the generator for this parser.
	 */
	protected Generator generator() {
		return generator;
	}

	/**
	 * Parse a file.
	 * @param file The file to be parsed.
	 */
	public abstract void parse(File file) throws IOException;
}