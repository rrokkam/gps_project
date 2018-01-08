package parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.io.IOException;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser for XML files. This parser knows nothing about the OSM format.
 * Instead, it delegates the creation of OSM-related objects to an OSMGenerator.
 */
public class XMLParser extends Parser {

	/**
	 * Parse an OpenStreetMap XML stream. This will cause the XMLReader to make
	 * calls to the StartElement and EndElement methods of the XMLHandler.
	 */
	public void parse(File file) throws IOException {
		parse(new FileInputStream(file));
	}
	
	/**
	 * Parse an OpenStreetMap XML stream. This will cause the XMLReader to make
	 * calls to the StartElement and EndElement methods of the XMLHandler.
	 */
	public void parse(FileInputStream stream) throws IOException {
		XMLReader reader;
		try {
			reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
			throw new IOException(e);
		}
		reader.setContentHandler(new XMLHandler());
		try {
			InputSource source = new InputSource(stream);
			reader.parse(source);
		} catch (SAXException e) {
			throw new IOException(e);
		} finally {
			if (stream != null)
				stream.close();
		}
	}

	/**
	 * Handler for the XML reader, which will make calls to the current
	 * generator as it finds new elements.
	 */
	class XMLHandler extends DefaultHandler {

		/**
		 * This method is called by the SAX parser when it encounters a start
		 * tag, and notifies the generator that an element has started.
		 */
		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
			HashMap<String, String> attributes = new HashMap<String, String>();
			for (int i = 0; i < atts.getLength(); i++)
				attributes.put(atts.getQName(i), atts.getValue(i));
			if(generator() ==null)
				System.out.println("nullgenerator");
			generator().startElement(qName, attributes);
		}

		/**
		 * This method is called by the SAX parser when it encounters an end
		 * tag, and notifies the generator that an element has ended.
		 */
		@Override
		public void endElement(String namespaceURI, String localName, String qName) {
			generator().endElement(qName);
		}
		
		/**
		 * This method is called by the SAX parser when it encounters the start of a document.
		 */
		@Override
		public void startDocument() {
			generator().startGeneration();
		}
		
		/**
		 * This method is called by the SAX parser when it encounters the end of a document.
		 */
		@Override
		public void endDocument() {
			generator().endGeneration();
		}
	}
}