package trafic.elements;

public class SensorEdges {

    private Sensor capteur;
    private Sensor capteurIn;
    private Sensor capteurOut;

    public SensorEdges(Sensor c, Sensor cIn, Sensor cOut) {
	capteur = c;
	capteurIn = cIn;
	capteurOut = cOut;
    }

    public Sensor getCapteur() {
	return capteur;
    }

    public void setCapteur(Sensor capteur) {
	this.capteur = capteur;
    }

    public Sensor getCapteurIn() {
	return capteurIn;
    }

    public void setCapteurIn(Sensor capteurIn) {
	this.capteurIn = capteurIn;
    }

    public Sensor getCapteurOut() {
	return capteurOut;
    }

    public void setCapteurOut(Sensor capteurOut) {
	this.capteurOut = capteurOut;
    }

    @Override
    public String toString() {
	return capteurIn.toString() + " - " + capteur.toString() + " - "
		+ capteurOut.toString();
    }

}
