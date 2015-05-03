package trafic.elements;

import java.util.ArrayList;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise SensorEdges
 */
public class SensorEdges {

    private Sensor capteur;
    private ArrayList<Sensor> capteurIn = new ArrayList<Sensor>();
    private ArrayList<Sensor> capteurOut = new ArrayList<Sensor>();

    /**
     * Constructeur
     * 
     * @param c
     *            : capteur
     * @param cIn
     *            : capteur In
     * @param cOut
     *            : capteur Out
     */
    public SensorEdges(Sensor c, Sensor cIn, Sensor cOut) {
	capteur = c;
	if (cIn != null)
	    capteurIn.add(cIn);
	if (cOut != null)
	    capteurOut.add(cOut);
    }

    /**
     * 
     * @return capteur
     */
    public Sensor getCapteur() {
	return capteur;
    }

    /**
     * Modifie le capteur
     * 
     * @param capteur
     *            : nouveau capteur
     */
    public void setCapteur(Sensor capteur) {
	this.capteur = capteur;
    }

    /**
     * 
     * @return premier capteur In dans la liste
     */
    public Sensor getCapteurIn() {
	return capteurIn.get(0);
    }

    /**
     * Ajoute le capteur In a la liste
     * 
     * @param capteur
     *            : nouveau capteur In
     */
    public void setCapteurIn(Sensor capteurIn) {
	this.capteurIn.add(capteurIn);
    }

    /**
     * 
     * @return premier capteur Out dans la liste
     */
    public Sensor getCapteurOut() {
	return capteurOut.get(0);
    }

    /**
     * Ajoute le capteur Out a la liste
     * 
     * @param capteur
     *            : nouveau capteur Out
     */
    public void setCapteurOut(Sensor capteurOut) {
	this.capteurOut.add(capteurOut);
    }

    /**
     * Retourne la liste de capteurs In
     * 
     * @return capteurIn
     */
    public ArrayList<Sensor> getCapteurInList() {
	return capteurIn;
    }

    /**
     * Retourne la liste de capteurs Out
     * 
     * @return capteurOut
     */
    public ArrayList<Sensor> getCapteurOutList() {
	return capteurOut;
    }

    @Override
    public String toString() {
	return capteurIn.toString() + " - " + capteur.toString() + " - "
		+ capteurOut.toString();
    }

}
