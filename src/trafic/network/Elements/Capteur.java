package trafic.network.Elements;

public class Capteur {

    private final int id;
    private final String type;

    public Capteur() {
	this.id = -1;
	this.type = "";
    }

    public Capteur(int id, String type) {
	this.id = id;
	this.type = type;
    }

    public int getId() {
	return id;
    }

    public String getType() {
	return type;
    }

}
