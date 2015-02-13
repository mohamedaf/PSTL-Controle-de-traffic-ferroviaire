package trafic.test;

import trafic.cparser.parser.Parser;

public class ParserTest {

    public static void main(String[] args) {
	try {
	    String chaine = "<pcf reqid=\"1\" type = \"answer\"><scenario id=\"1\"/><topography><sensor-edges><capteur id=\"1\" type=\"station\"/><in><capteur id=\"5\" type=\"station\"/></in><out><capteur id=\"2\" type=\"station\"/></out></sensor-edges><sensor-edges><capteur id=\"2\" type=\"station\"/><in><capteur id=\"1\" type=\"station\"/></in><out><capteur id=\"3\" type=\"station\"/></out></sensor-edges><sensor-edges><capteur id=\"3\" type=\"station\"/><in><capteur id=\"2\" type=\"station\"/></in><out><capteur id=\"4\" type=\"station\"/></out></sensor-edges><sensor-edges><capteur id=\"4\" type=\"station\"/><in><capteur id=\"3\" type=\"station\"/></in><out><capteur id=\"5\" type=\"station\"/></out></sensor-edges><sensor-edges><capteur id=\"5\" type=\"station\"/><in><capteur id=\"4\" type=\"station\"/></in><out><capteur id=\"1\" type=\"station\"/></out></sensor-edges></topography><init><position><before><capteur id=\"1\" type=\"station\"/></before><train id=\"2\" action=\"stop\" direction=\"forward\"/><after><capteur id=\"2\" type=\"station\"/></after></position><position><before><capteur id=\"4\" type=\"station\"/></before><train id=\"4\" action=\"stop\" direction=\"forward\"/><after><capteur id=\"5\" type=\"station\"/></after></position></init><lights><light id=\"1\" color=\"red\"/><light id=\"2\" color=\"red\"/><light id=\"3\" color=\"red\"/><light id=\"4\" color=\"red\"/><light id=\"5\" color=\"red\"/></lights></pcf>";

	    Parser p = new Parser(null);
	    p.parse(chaine);

	    System.out.println(p.getPcf().toString());

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }
}
