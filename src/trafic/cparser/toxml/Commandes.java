package trafic.cparser.toxml;

import trafic.enums.PCFType;
import trafic.enums.Status;

/**
 * @author KOBROSLI - AFFES
 *
 */
public class Commandes {

    /**
     * Retourne une chaine representant une balise pcf, qui est la balise de
     * premier niveau dans la hierarchie
     * 
     * @param reqid
     *            : identifiant de la balise
     * @param type
     *            : (request | advise | answer)
     * @param content
     *            : contenu de la balise
     * @return une chaine representant la balise pcf
     */
    public static String pcf(int reqid, PCFType type, String content) {
	return "<pcf reqid=\"" + reqid + "\" type=\"" + type + "\">" + content
		+ "</pcf>";
    }

    /**
     * Retourne une chaine representant une balise hello, requete
     * d’initialisation d’une session
     * 
     * @param id
     *            : identifiant de la balise
     * @return une chaine representant la balise hello
     */
    public static String hello(int id) {
	return "<hello id=\"" + id + "\"/>";
    }

    /**
     * Retourne une chaine representant une balise info, cet element est
     * principalement destine aux messages d’informations
     * 
     * @param status
     *            : (ok | ko)
     * @param content
     *            : message contenant l'information
     * @return une chaine representant la balise info
     */
    public static String info(Status status, String content) {
	String s = "<info status=\"";
	s += status;
	if (content == null)
	    content = "";
	return s + "\">" + content + "</info>";
    }

    /**
     * Retourne une chaine representant une balise start, requete destineee a
     * demarrer le fonctionnement du systeme
     * 
     * @return une chaine representant la balise start
     */
    public static String start() {
	return "<start></start>";
    }

    /**
     * Retourne une chaine representant une balise bye, requete d’information de
     * fin de session
     * 
     * @return une chaine representant la balise bye
     */
    public static String bye() {
	return "<bye></bye>";
    }

    /**
     * Retourne une chaine representant une balise set, requete de commande des
     * feux de signalisation, des vehicules ferroviaires ou des aiguillages
     * 
     * @param msg
     *            : contenu de la balise
     * @return une chaine representant la balise set
     */
    public static String set(String msg) {
	return "<set>" + msg + "</set>";
    }

    /**
     * Retourne une chaine representant une balise train
     * 
     * @param id
     *            : identifiant du train
     * @param action
     *            : true=start, false=stop
     * @param dir
     *            : true=forward, false = backward
     * @return une chaine representant la balise train
     */
    public static String train(int id, boolean action, boolean dir) {
	String s = "<train id=\"" + id + "\" action=\"";
	if (action)
	    s += "start";
	else
	    s += "stop";
	s += "\" dir=\"";
	if (dir)
	    s += "forward";
	else
	    s += "backward";
	s += "\"/>";
	return s;
    }

    /**
     * Retourne une chaine representant une balise switchXml
     * 
     * @param id
     *            : identifiant de la balise switch-edge
     * @param branch
     *            : nouvelle valeur de branch indiquant sa position
     * @return une chaine representant la balise switchXml
     */
    public static String switchXml(int id, int branch) {
	String s = "<switch id=\"" + id + "\" pos=\"" + branch + "\"/>";
	System.out.println(s);
	return s;
    }

}
