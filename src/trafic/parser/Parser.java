package trafic.parser;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import trafic.network.Elements.Capteur;
import trafic.network.Elements.Info;
import trafic.network.Elements.Init;
import trafic.network.Elements.Light;
import trafic.network.Elements.Lights;
import trafic.network.Elements.PcfHello;
import trafic.network.Elements.PcfStart;
import trafic.network.Elements.Position;
import trafic.network.Elements.Scenario;
import trafic.network.Elements.SensorEdges;
import trafic.network.Elements.Topography;
import trafic.network.Elements.Train;
import trafic.network.Enum.Color;
import trafic.network.Enum.Status;
import trafic.network.Enum.TrainAction;
import trafic.network.Enum.TrainDirection;

public class Parser {

    private final PcfHello pcf;
    private final PcfStart pcf2;

    public Parser() {
	pcf = new PcfHello();
	pcf2 = new PcfStart();
    }

    public PcfHello getPcf() {
	return pcf;
    }

    public PcfStart getPcf2() {
	return pcf2;
    }

    public void parseHello(String xml) {
	DocumentBuilder parser;
	Document document;

	try {
	    parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new InputSource(new StringReader(xml)));

	    parseHelloAnswer(document);
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException | IOException e) {
	    e.printStackTrace();
	}

    }

    public void parseHelloAnswer(final Node n) {
	int i;
	NodeList tmp;
	Element el;
	Capteur capteur, capteurIn, capteurOut, before, after;
	Train train;

	try {
	    switch (n.getNodeType()) {
	    case Node.DOCUMENT_NODE: {
		parseHelloAnswer(((Document) n).getDocumentElement());
	    }
	    case Node.ELEMENT_NODE: {
		final Element e = (Element) n;
		final String name = e.getTagName();

		switch (name) {
		case "pcf": {
		    /* Ajouter les attributs de pcf */
		    pcf.setReqid(Integer.parseInt(e.getAttribute("reqid")));
		    pcf.setType(e.getAttribute("type"));

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de pcf */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseHelloAnswer(tmp.item(i));
		    }
		}
		case "scenario": {
		    /* Ajouter les attributs de la balise scenario */
		    pcf.setScenario(new Scenario(Integer.parseInt(e
			    .getAttribute("id"))));
		}
		case "topography": {
		    pcf.setTopography(new Topography());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de topography */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseHelloAnswer(tmp.item(i));
		    }
		}
		case "sensor-edges": {
		    tmp = n.getChildNodes();

		    el = (Element) tmp.item(0);

		    capteur = new Capteur(Integer.parseInt(el
			    .getAttribute("id")), el.getAttribute("type"));

		    /* Balise in */

		    el = (Element) tmp.item(1);

		    NodeList tmp2 = el.getChildNodes();
		    Element ell = (Element) tmp2.item(0);

		    capteurIn = new Capteur(Integer.parseInt(ell
			    .getAttribute("id")), ell.getAttribute("type"));

		    /* Balise out */

		    el = (Element) tmp.item(2);

		    tmp2 = el.getChildNodes();
		    ell = (Element) tmp2.item(0);

		    capteurOut = new Capteur(Integer.parseInt(ell
			    .getAttribute("id")), ell.getAttribute("type"));

		    pcf.getTopography().addSensorEdges(
			    new SensorEdges(capteur, capteurIn, capteurOut));
		}
		case "init": {
		    pcf.setInit(new Init());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de init */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseHelloAnswer(tmp.item(i));
		    }
		}
		case "position": {
		    tmp = n.getChildNodes();

		    el = (Element) tmp.item(0);

		    before = new Capteur(
			    Integer.parseInt(el.getAttribute("id")),
			    el.getAttribute("type"));

		    el = (Element) tmp.item(1);

		    int TrainId = Integer.parseInt(el.getAttribute("id"));

		    String action = new String(el.getAttribute("action"));
		    String direction = new String(el.getAttribute("direction"));

		    String action1 = new String("start");
		    String action2 = new String("stop");
		    String direction1 = new String("forward");
		    String direction2 = new String("backward");

		    if (action.equals(action1) && direction.equals(direction1)) {
			train = new Train(TrainId, TrainAction.start,
				TrainDirection.forward);
		    } else if (action.equals(action1)
			    && direction.equals(direction2)) {
			train = new Train(TrainId, TrainAction.start,
				TrainDirection.backward);
		    } else if (action.equals(action2)
			    && direction.equals(direction1)) {
			train = new Train(TrainId, TrainAction.stop,
				TrainDirection.forward);
		    } else {
			train = new Train(TrainId, TrainAction.stop,
				TrainDirection.backward);
		    }

		    el = (Element) tmp.item(2);
		    after = new Capteur(
			    Integer.parseInt(el.getAttribute("id")),
			    el.getAttribute("type"));

		    pcf.getInit().addPosition(
			    new Position(before, after, train));
		}
		case "lights": {
		    pcf.setLights(new Lights());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de lights */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseHelloAnswer(tmp.item(i));
		    }
		}
		case "light": {
		    pcf.getLights().addLight(
			    new Light(Integer.parseInt(e.getAttribute("id")),
				    Color.red));
		    /* Car a ce stade tous les feux sont rouges */
		}

		default: {
		    final String msg = "Unknown element name: " + name;
		    throw new ParseException(msg);
		}
		}
	    }
	    default: {
		final String msg = "Unknown node type: " + n.getNodeName();
		throw new ParseException(msg);
	    }
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void parseStart(String xml) {
	DocumentBuilder parser;
	Document document;

	try {
	    parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new InputSource(new StringReader(xml)));

	    parseStartAnswer(document);
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException | IOException e) {
	    e.printStackTrace();
	}

    }

    public void parseStartAnswer(final Node n) {
	NodeList tmp;

	try {
	    switch (n.getNodeType()) {
	    case Node.DOCUMENT_NODE: {
		parseStartAnswer(((Document) n).getDocumentElement());
	    }
	    case Node.ELEMENT_NODE: {
		final Element e = (Element) n;
		final String name = e.getTagName();

		switch (name) {
		case "pcf": {
		    /* Ajouter les attributs de pcf */
		    pcf2.setReqid(Integer.parseInt(e.getAttribute("reqid")));
		    pcf2.setType(e.getAttribute("type"));

		    tmp = n.getChildNodes();

		    /* appel recursif sur la balise info */
		    parseStartAnswer(tmp.item(0));

		}
		case "info": {
		    String status = new String(e.getAttribute("status"));
		    String status1 = new String("ok");
		    String status2 = new String("ko");

		    if (status.equals(status1)) {
			pcf2.setInfo(new Info(Status.ok, e.getTextContent()));
		    } else if (status.equals(status2)) {
			pcf2.setInfo(new Info(Status.ko, e.getTextContent()));
		    } else {
			throw new ParseException(
				"status inconnu dans la balise info");
		    }
		}

		default: {
		    final String msg = "Unknown element name: " + name;
		    throw new ParseException(msg);
		}
		}
	    }
	    default: {
		final String msg = "Unknown node type: " + n.getNodeName();
		throw new ParseException(msg);
	    }
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
