package trafic.cparser.parser;

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

import trafic.control.Controller;
import trafic.elements.Info;
import trafic.elements.Init;
import trafic.elements.Light;
import trafic.elements.Lights;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Scenario;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.elements.SwitchEdges;
import trafic.elements.Topography;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.SwitchPos;
import trafic.enums.SwitchType;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.IParser;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe gerant le parse d'un message XML recu du moniteur (serveur)
 */
public class Parser implements IParser {
    private final Pcf pcf;
    private Controller controller;

    /**
     * Constructeur
     * 
     * @param controller
     *            : instance du Controller
     */
    public Parser(Controller controller) {
	pcf = new Pcf();
	this.controller = controller;
	this.controller.setPCF(pcf);
    }

    @Override
    public void parse(String xml) {
	DocumentBuilder parser;
	Document document;

	try {
	    parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new InputSource(new StringReader(xml)));

	    parseAnswer(document);
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException | IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Methode recursive principale du parse. Construction de l'objet PCF en
     * parsant le fichier XML
     * 
     * @param n
     *            : noeud courant
     */
    private void parseAnswer(final Node n) {
	int i;
	NodeList tmp;
	Element el;
	Sensor capteur, capteurIn = null, capteurOut, before, after;
	Train train;

	try {
	    switch (n.getNodeType()) {
	    case Node.DOCUMENT_NODE: {
		final Document d = (Document) n;
		parseAnswer(d.getDocumentElement());

		break;
	    }
	    case Node.ELEMENT_NODE: {
		final Element e = (Element) n;
		String name = e.getTagName();

		switch (name) {
		case "pcf": {
		    /* Ajouter les attributs de pcf */
		    pcf.setReqid(Integer.parseInt(e.getAttribute("reqid")));
		    pcf.setType(e.getAttribute("type"));

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de pcf */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseAnswer(tmp.item(i));
		    }

		    Element nod = (Element) tmp.item(0);
		    if (nod.getTagName().equals("olleh")) {
			System.out.println("Notify init !!!!");
			controller.notifyInit();
		    }

		    break;
		}
		case "scenario": {
		    int t = Integer.parseInt(e.getAttribute("id"));

		    /* Ajouter les attributs de la balise scenario */
		    pcf.setScenario(new Scenario(t));

		    break;
		}
		case "topography": {
		    pcf.setTopography(new Topography());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de topography */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseAnswer(tmp.item(i));
		    }

		    break;
		}
		case "sensor-edges": {
		    tmp = n.getChildNodes();

		    el = (Element) tmp.item(0);

		    capteur = new Sensor(
			    Integer.parseInt(el.getAttribute("id")),
			    el.getAttribute("type"));

		    /* Balise in */

		    el = (Element) tmp.item(1);

		    Element ell = (Element) el.getFirstChild();
		    if (ell != null)
			capteurIn = new Sensor(Integer.parseInt(ell
				.getAttribute("id")), ell.getAttribute("type"));

		    /* Balise out */

		    el = (Element) tmp.item(2);

		    ell = (Element) el.getFirstChild();

		    capteurOut = new Sensor(Integer.parseInt(ell
			    .getAttribute("id")), ell.getAttribute("type"));

		    pcf.getTopography().addSensorEdges(
			    new SensorEdges(capteur, capteurIn, capteurOut));

		    break;
		}
		case "switch-edges": {
		    SwitchType p;

		    if (e.getAttribute("type").equalsIgnoreCase("1-2"))
			p = SwitchType._1_2;
		    else if (e.getAttribute("type").equalsIgnoreCase("2-1"))
			p = SwitchType._2_1;
		    else
			throw new ParseException("SwitchEdges type "
				+ e.getAttribute("type") + " non reconnu");

		    /*
		     * On a mis b1 car de toute façon à l'initialisation on le
		     * modifie donc n'affecte pas
		     */

		    pcf.getTopography().addSwitchEdges(
			    new SwitchEdges(Integer.parseInt(e
				    .getAttribute("id")), p, Integer.parseInt(e
				    .getAttribute("trunk")), Integer.parseInt(e
				    .getAttribute("branch0")), Integer
				    .parseInt(e.getAttribute("branch1")),
				    SwitchPos.b1));

		    break;
		}
		case "init": {
		    pcf.setInit(new Init());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de init */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseAnswer(tmp.item(i));
		    }

		    break;
		}
		case "position": {
		    tmp = n.getChildNodes();

		    /* recuperation de la balise before */
		    el = (Element) tmp.item(0);

		    Element ell = (Element) el.getFirstChild();

		    before = new Sensor(
			    Integer.parseInt(ell.getAttribute("id")),
			    ell.getAttribute("type"));

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

		    /* Balise after */
		    el = (Element) tmp.item(2);

		    ell = (Element) el.getFirstChild();

		    after = new Sensor(
			    Integer.parseInt(ell.getAttribute("id")),
			    ell.getAttribute("type"));

		    pcf.getInit().addPosition(
			    new Position(before, after, train));

		    break;
		}
		case "lights": {
		    pcf.setLights(new Lights());

		    tmp = n.getChildNodes();

		    /* appel recursif sur les balises fils de lights */
		    for (i = 0; i < tmp.getLength(); i++) {
			parseAnswer(tmp.item(i));
		    }

		    break;
		}
		case "light": {
		    pcf.getLights().addLight(
			    new Light(Integer.parseInt(e.getAttribute("id")),
				    Color.red));
		    /* Car a ce stade tous les feux sont rouges */

		    break;
		}
		case "info": {
		    String status = new String(e.getAttribute("status"));
		    String status1 = new String("ok");
		    String status2 = new String("ko");

		    if (status.equals(status1)) {
			pcf.addInfo(new Info(Status.ok, e.getTextContent()));
		    } else if (status.equals(status2)) {
			pcf.addInfo(new Info(Status.ko, e.getTextContent()));
		    } else {
			throw new ParseException(
				"status inconnu dans la balise info");
		    }

		    break;
		}
		case "up": {
		    tmp = n.getChildNodes();

		    /* Notifier pour chaque fils */

		    for (i = 0; i < tmp.getLength(); i++) {
			parseUp(tmp.item(i));
		    }
		    break;
		}
		case "olleh": {
		    break;
		}
		case "bye": {
		    break;
		}

		default: {
		    final String msg = "Unknown element name: " + name;
		    throw new ParseException(msg);
		}
		}

		break;
	    }
	    default: {
		final String msg = "Unknown node type: " + n.getNodeName();
		System.out.println(msg);
		throw new ParseException(msg);
	    }
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Methode traitant une requete Up du serveur
     * 
     * @param n
     *            : noeud contenant les informations de la balise
     */
    public void parseUp(Node n) {
	final Element e = (Element) n;
	String name = e.getTagName();

	try {
	    switch (name) {
	    case "capteur": {
		/* Notifier controlleur d'un up */
		controller.notifyUp(Integer.parseInt(e.getAttribute("id")));
		break;
	    }
	    default: {
		final String msg = "Unknown element name: " + name;
		throw new ParseException(msg);
	    }
	    }
	} catch (ParseException e1) {
	    e1.printStackTrace();
	}
    }

    /**
     * Verifier la nature du statut de la balise info
     * 
     * @param xml
     *            : chaine de la balise
     * @return true si Statut ok false sinon
     */
    public static boolean isOkInfo(String xml) {
	DocumentBuilder parser;
	Document document;

	try {
	    parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    document = parser.parse(new InputSource(new StringReader(xml)));

	    return parseIsOkInfo(document);
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException | IOException e) {
	    e.printStackTrace();
	}

	return false;
    }

    /**
     * Verifier la nature du statut de la balise info
     * 
     * @param n
     *            : noeud courant contenant les informations de la balise
     * @return true si Statut ok false sinon
     */
    public static boolean parseIsOkInfo(Node n) {
	try {
	    switch (n.getNodeType()) {
	    case Node.DOCUMENT_NODE: {
		final Document d = (Document) n;
		parseIsOkInfo(d.getDocumentElement());

		break;
	    }
	    case Node.ELEMENT_NODE: {
		final Element e = (Element) n;
		String name = e.getTagName();

		switch (name) {
		case "info": {
		    if (e.getAttribute("status").equalsIgnoreCase("ok"))
			return true;

		    return false;
		}

		default: {
		    final String msg = "Unknown element name: " + name;
		    throw new ParseException(msg);
		}
		}
	    }
	    default: {
		final String msg = "Unknown node type: " + n.getNodeName();
		System.out.println(msg);
		throw new ParseException(msg);
	    }
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return false;
    }

    /**
     * retourner l'objet pcf representant l'etat actuel du circuit
     * 
     * @return pcf
     */
    public Pcf getPcf() {
	return pcf;
    }
}
