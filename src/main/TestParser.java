package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Sample parser for reading Open Street Map XML format files.
 * Illustrates the use of SAXParser to parse XML.
 *
 * @author E. Stark
 * @date September 20, 2009
 * 
 * Edited by
 * @author rohithrokkam
 */
class TestParser {

    /** OSM file from which the input is being taken. */
    private File file;

    /**
     * Initialize an OSMParser that takes data from a specified file.
     *
     * @param s The file to read.
     * @throws IOException
     */
    public TestParser(File f) {
        file = f;
    }

    /**
     * Parse the OSM file underlying this OSMParser.
     */
    public void parse()
        throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        OSMHandler handler = new OSMHandler();
        xmlReader.setContentHandler(handler);
        InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            InputSource source = new InputSource(stream);
            xmlReader.parse(source);
        } catch(IOException e) {
            throw e;
        } finally {
            if(stream != null)
                stream.close();
        }
    }

    /**
     * Handler class used by the SAX XML parser.
     * The methods of this class are called back by the parser when
     * XML elements are encountered.
     */
    class OSMHandler extends DefaultHandler {
    	// Main Type, string key, List<String> Values
    	HashMap<String, HashMap<String, ArrayList<String>>> data
    		= new HashMap<String, HashMap<String, ArrayList<String>>>();
    	

        /** Current character data. */
        private String cdata;

        /** Attributes of the current element. */
        private Attributes attributes;

        /**
         * Get the most recently encountered CDATA.
         */
        public String getCdata() {
            return cdata;
        }

        /**
         * Get the attributes of the most recently encountered XML element.
         */
        public Attributes getAttributes() {
            return attributes;
        }

        /**
         * Method called by SAX parser when start of document is encountered.
         */
        public void startDocument() {
            System.out.println("startDocument");
        }

        /**
         * Method called by SAX parser when end of document is encountered.
         */
        public void endDocument() {
            System.out.println("endDocument");
            
            for(String s : data.keySet()) {
            	System.out.println(s);
            	for(String t : data.get(s).keySet()) {
            		System.out.println("\t"+t);
            		for(String u : data.get(s).get(t)) {
            			System.out.println("\t\t"+u);
            		}
            	}
            }
            	
        }

        /**
         * Method called by SAX parser when start tag for XML element is
         * encountered.
         */
        public void startElement(String namespaceURI, String localName,
                                 String qName, Attributes atts) {
        	if(!(data.containsKey(qName))) 
        		data.put(qName, new HashMap<String, ArrayList<String>>());
        	
        	HashMap<String, ArrayList<String>> map = data.get(qName);
        	for(int i = 0; i < atts.getLength(); i++) {
        		
        	}
        	
        	
        	for(int i = 0; i < atts.getLength(); i++) {
        		if(!(map.containsKey(atts.getQName(i)) && qName.equals("way")))
        			map.put(atts.getQName(i), new ArrayList<String>());
        		
        		// Removing very common tags to avoid cluttering the results.
        		
//        		if(!(atts.getQName(i).equals("id")) && 
//        		   !(atts.getQName(i).equals("timestamp")) &&
//        		   !(atts.getQName(i).equals("uid")) &&
//        		   !(atts.getQName(i).equals("lat")) &&
//        		   !(atts.getQName(i).equals("ref")) &&
//        		   !(atts.getQName(i).equals("lon")) &&
//        		   !(atts.getQName(i).equals("changeset")) &&
//        		   !(atts.getQName(i).equals("user")) &&
//        				!(map.get(atts.getQName(i)).contains(atts.getValue(i)))) {
            		map.get(atts.getQName(i)).add(atts.getValue(i));
        		
        	}
        	
        	/*
            attributes = atts;
            System.out.println("startElement: " + namespaceURI + ","
                               + localName + "," + qName);
            if(atts.getLength() > 0)
                showAttrs(atts);
            */
        }

        /**
         * Method called by SAX parser when end tag for XML element is
         * encountered.  This can occur even if there is no explicit end
         * tag present in the document.
         */
        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXParseException {
        	/*
            System.out.println("endElement: " + namespaceURI + ","
                               + localName + "," + qName);
            */
        }

        /**
         * Method called by SAX parser when character data is encountered.
         */
        public void characters(char[] ch, int start, int length)
            throws SAXParseException {
            // OSM files apparently do not have interesting CDATA.
            //System.out.println("cdata(" + length + "): '"
            //                 + new String(ch, start, length) + "'");
            cdata = (new String(ch, start, length)).trim();
        }

        /**
         * Auxiliary method to display the most recently encountered
         * attributes.
         */
//        private void showAttrs(Attributes atts) {
//            for(int i=0; i < atts.getLength(); i++) {
//                String qName = atts.getQName(i);
//                String type = atts.getType(i);
//                String value = atts.getValue(i);
//                System.out.println("\t" + qName + "=" + value
//                                   + "[" + type + "]");
//            }
//        }
    }

    /**
     * Test driver.  Takes filenames to be parsed as command-line arguments.
     */
    public static void main(String[] args) throws Exception {
        for(int i = 0; i < args.length; i++) {
            TestParser prsr = new TestParser(new File(args[i]));
            prsr.parse();
        }
    }
}