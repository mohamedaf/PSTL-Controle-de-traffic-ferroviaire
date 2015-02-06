package trafic.network;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class test {

    public static void main(String[] args) {
	try {
	    String chaine = "<utilisateur><nom validator=\"required('login')\">jerome</nom>"
		    + "<mdp validator=\"required('mot de passe');minLength(6)\">password</mdp>"
		    + "</utilisateur>";

	    DocumentBuilder parser = DocumentBuilderFactory.newInstance()
		    .newDocumentBuilder();
	    Document document = parser.parse(new InputSource(new StringReader(
		    chaine)));

	    System.out.println(document);

	    Element nom = (Element) document.getElementsByTagName("nom")
		    .item(0);

	    String validator = nom.getAttribute("validator");
	    System.out.println(validator);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
