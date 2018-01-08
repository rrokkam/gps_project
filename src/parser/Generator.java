package parser;

import java.util.HashMap;

/**
 * Interface whose implementers can generate elements by taking
 * type and attribute information about elements.
 * @author rohithrokkam
 */
public interface Generator {

	/**
	 * Method called by a parser when it encounters the beginning
	 * of a new element. Creates a new OSMElement and assigns it
	 * attributes.
	 */
	default void startElement(String type, HashMap<String, String> attributes) {
	}

	/**
	 * Method called by a parser when it encounters the end of
	 * an element. Adds the finished element to the list of elements
	 * this OSMGenerator has processed.
	 */
	default void endElement(String type) {
	}

	/**
	 * Method called by a parser when it encounters the start of
	 * a document.
	 */
	default void startGeneration() {
	}

	/**
	 * Method called by a parser when it encounters the end of
	 * a document.
	 */
	default void endGeneration() {
	}
}
