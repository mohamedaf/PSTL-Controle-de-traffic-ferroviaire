package trafic.elements;

import trafic.enums.SensorType;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Sensor
 */
public class Sensor {

    private int id;
    private SensorType type;

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant du capteur
     * @param type
     *            : type du capteur (canton | station)
     */
    public Sensor(int id, SensorType type) {
	this.id = id;
	this.type = type;
    }

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant du capteur
     * @param type
     *            : type du capteur (canton | station)
     */
    public Sensor(int id, String type) {
	this.id = id;
	if (type.equals("canton"))
	    this.type = SensorType.canton;
	else
	    this.type = SensorType.station;
    }

    /**
     * 
     * @return identifiant du capteur
     */
    public int getId() {
	return id;
    }

    /**
     * @return type du capteur (canton | station)
     */
    public SensorType getType() {
	return type;
    }

    @Override
    public String toString() {
	return "Capteur " + id + " type " + type;
    }

    @Override
    public Sensor clone() {
	return new Sensor(id, type);
    }

}
