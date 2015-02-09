package trafic.elements;

public class SensorEdges {

	private Capteur capteur;
	private Capteur capteurIn;
	private Capteur capteurOut;

	public SensorEdges(Capteur c, Capteur cIn, Capteur cOut) {
		capteur = c;
		capteurIn = cIn;
		capteurOut = cOut;
	}

	public Capteur getCapteur() {
		return capteur;
	}

	public void setCapteur(Capteur capteur) {
		this.capteur = capteur;
	}

	public Capteur getCapteurIn() {
		return capteurIn;
	}

	public void setCapteurIn(Capteur capteurIn) {
		this.capteurIn = capteurIn;
	}

	public Capteur getCapteurOut() {
		return capteurOut;
	}

	public void setCapteurOut(Capteur capteurOut) {
		this.capteurOut = capteurOut;
	}

	public String toString() {
		return capteurIn.toString() + " - " + capteur.toString() + " - "
				+ capteurOut.toString();
	}

}
