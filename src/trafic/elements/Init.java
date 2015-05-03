package trafic.elements;

import java.util.ArrayList;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Init
 */
public class Init {

    private ArrayList<Position> positions;

    /**
     * Constructeur
     */
    public Init() {
	positions = new ArrayList<Position>();
    }

    /**
     * ajouter un element a la liste positions
     * 
     * @param p
     *            : element a ajouter a la liste
     */
    public void addPosition(Position p) {
	positions.add(p);
    }

    /**
     * 
     * @param i
     *            : indexe de l'element
     * @return element a l'indexe i de la liste
     */
    public Position getPosition(int i) {
	return positions.get(i);
    }

    /**
     * 
     * @return la liste positions
     */
    public ArrayList<Position> getListPositions() {
	return positions;
    }

    /**
     * 
     * @param id
     *            : identifiant du train
     * @return train d'identifiant id s'il existe dans la liste, null sinon
     */
    public Position getPositionByTrainId(int id) {
	for (Position p : positions) {
	    if (p.getTrain().getId() == id)
		return p;
	}
	return null;
    }

    /**
     * 
     * @param sensorId
     * @return position ayant le comme capteur after le capteur ayant l'id =
     *         sensorId
     */
    public Position getPositionByAfterSensor(int sensorId) {
	for (Position p : positions) {
	    if (p.getAfter().getId() == sensorId) {
		return p;
	    }
	}
	return null;
    }

    @Override
    public String toString() {
	String s = "";
	for (Position pos : positions) {
	    s += pos.toString() + "\n";
	}
	return s;
    }

}
