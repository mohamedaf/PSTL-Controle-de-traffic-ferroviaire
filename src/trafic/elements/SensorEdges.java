package trafic.elements;

import java.util.ArrayList;

public class SensorEdges {

    private Sensor capteur;
    private ArrayList<Sensor> capteurIn;
    private ArrayList<Sensor> capteurOut;

    public SensorEdges(Sensor c, Sensor cIn, Sensor cOut) {
	capteur = c;
	capteurIn = new ArrayList<Sensor>();
	capteurOut = new ArrayList<Sensor>();
	capteurIn.add(cIn);
	capteurOut.add(cOut);
    }

    public Sensor getCapteur() {
	return capteur;
    }

    public void setCapteur(Sensor capteur) {
	this.capteur = capteur;
    }

    public Sensor getCapteurIn() {
	return capteurIn.get(0);
    }

    public void setCapteurIn(Sensor capteurIn) {
	this.capteurIn.add(capteurIn);
    }

    public Sensor getCapteurOut() {
	return capteurOut.get(0);
    }

    public void setCapteurOut(Sensor capteurOut) {
	this.capteurOut.add(capteurOut);
    }

    public ArrayList<Sensor> getCapteurInList() {
	return capteurIn;
    }

    public ArrayList<Sensor> getCapteurOutList() {
	return capteurOut;
    }

    @Override
    public String toString() {
	return capteurIn.toString() + " - " + capteur.toString() + " - "
		+ capteurOut.toString();
    }

}
