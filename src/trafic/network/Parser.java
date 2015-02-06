package trafic.network;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Parser {

    public void parseHello(String xml) {
	DocumentBuilder parser;
	Document document;

	try {
	    parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new InputSource(new StringReader(xml)));

	    /* parseHelloAnswer(document); */
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException | IOException e) {
	    e.printStackTrace();
	}

    }

    /* En cours */

    /*
     * public void parseHelloAnswer(final Node n) { try { switch
     * (n.getNodeType()) { case Node.DOCUMENT_NODE: {
     * parseHelloAnswer(((Document) n).getDocumentElement()); } case
     * Node.ELEMENT_NODE: { final Element e = (Element) n; final String name =
     * e.getTagName();
     * 
     * switch (name) { case "pcf": case "scenario": case "topography": case
     * "sensor-edges": case "capteur": case "in": case "out": case "init": case
     * "position": case "before": case "train": case "after": case "lights":
     * case "light":
     * 
     * default: { final String msg = "Unknown element name: " + name; throw new
     * ParseException(msg); } } } default: { final String msg =
     * "Unknown node type: " + n.getNodeName(); throw new ParseException(msg); }
     * }
     * 
     * } catch (Exception e) { e.printStackTrace(); } }
     */

    /* To Do */

    public void parseStartAnswer(String xml) {

    }

}
