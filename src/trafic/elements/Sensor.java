package trafic.elements;

public class Sensor {

    private int id;
    private String type;

    public Sensor(int id, String type) {
	this.id = id;
	this.type = type;
    }

    public int getId() {
	return id;
    }

    public String getType() {
	return type;
    }

    @Override
    public String toString() {
	return "Capteur " + id + " type " + type;
    }

}
