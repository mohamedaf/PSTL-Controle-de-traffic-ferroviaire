package trafic.elements;

import trafic.enums.SensorType;

public class Sensor {

    private int id;
    private SensorType type;

    public Sensor(int id, SensorType type) {
	this.id = id;
	this.type = type;
    }
    
    public Sensor(int id, String type) {
	this.id = id;
	if(type.equals("canton"))
		this.type = SensorType.canton;
	else
		this.type = SensorType.station;
    }

    public int getId() {
	return id;
    }

    public SensorType getType() {
	return type;
    }

    @Override
    public String toString() {
	return "Capteur " + id + " type " + type;
    }

    @Override
    public Sensor clone() {
	return new Sensor(id, type.toString());
    }

}
