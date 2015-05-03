package trafic.elements;

import java.util.ArrayList;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Topography
 */
public class Topography {

    private ArrayList<SensorEdges> sensorEdgesList;
    private ArrayList<SwitchEdges> switchEdgesList;

    /**
     * Constructeur
     */
    public Topography() {
	sensorEdgesList = new ArrayList<SensorEdges>();
	switchEdgesList = new ArrayList<SwitchEdges>();
    }

    /**
     * Ajouter un element a la liste contenant les switch-edges
     * 
     * @param s
     *            : element switch-edges
     */
    public void addSwitchEdges(SwitchEdges s) {
	switchEdgesList.add(s);
    }

    /**
     * 
     * @param i
     *            : index de l'element
     * @return element a l'indexe i de la liste
     */
    public SwitchEdges getSwitchEdges(int i) {
	return switchEdgesList.get(i);
    }

    /**
     * 
     * @param id
     *            : identifiant de l'element
     * @return element ayant l'identifiant id dans la liste
     */
    public SwitchEdges getSwitchEdgesById(int id) {
	for (SwitchEdges se : switchEdgesList) {
	    if (se.getId() == id) {
		return se;
	    }
	}
	return null;
    }

    /**
     * Retourne la liste contenant les switch-edges
     * 
     * @return switchEdgesList
     */
    public ArrayList<SwitchEdges> getSwitchEdgesList() {
	return switchEdgesList;
    }

    /**
     * Ajouter un element a la liste contenant les sensor-edges
     * 
     * @param s
     *            : element a ajouter a la liste
     */
    public void addSensorEdges(SensorEdges s) {
	sensorEdgesList.add(s);
    }

    /**
     * 
     * @param i
     *            : index de l'element a retourner
     * @return element a l'indexe i de la liste
     */
    public SensorEdges getSensorEdges(int i) {
	return sensorEdgesList.get(i);
    }

    /**
     * Retourne la liste contenant les sensor-edges
     * 
     * @return sensorEdgesList
     */
    public ArrayList<SensorEdges> getSensorEdgesList() {
	return sensorEdgesList;
    }

    /**
     * 
     * @param sensorId
     *            : identifiant de l'element
     * @return element ayant l'identifiant sensorId dans la liste
     */
    public SensorEdges getSensorEdgesById(int sensorId) {
	for (SensorEdges se : sensorEdgesList) {
	    if (se.getCapteur().getId() == sensorId) {
		return se;
	    }
	}
	return null;
    }

    /**
     * Verifie si l'un des capteur d'un switch-edges dans la liste correspond a
     * id
     * 
     * @param id
     *            : identifiant du capteur
     * @return true si correpondance false sinon
     */
    public boolean isPartOfSwitchEdge(int id) {
	for (SwitchEdges se : switchEdgesList) {
	    if (id == se.getTrunk() || id == se.getBranch0()
		    || id == se.getBranch1())
		return true;
	}
	return false;
    }

    /**
     * 
     * @param branchId
     *            : identifiant du capteur adjacent a l'une des branche de l'un
     *            des aiguillages de la liste
     * @return l'element switch-edges correspondant null sinon
     */
    public SwitchEdges getSwitchEdgesByBranch(int branchId) {
	for (SwitchEdges se : switchEdgesList) {
	    if (se.isBranch0(branchId) || se.isBranch1(branchId))
		return se;
	}
	return null;
    }

    /**
     * 
     * @param trunkId
     *            : identifiant du capteur adjacent au tronc
     * @return l'element switch-edges correspondant null sinon
     */
    public SwitchEdges getSwitchEdgesByTrunk(int trunkId) {
	for (SwitchEdges se : switchEdgesList) {
	    if (se.isTrunk(trunkId))
		return se;
	}
	return null;
    }

    @Override
    public String toString() {
	String s = "";
	for (SensorEdges sel : sensorEdgesList) {
	    s += sel.toString() + "\n";
	}
	for (SwitchEdges sel : switchEdgesList) {
	    s += sel.toString() + "\n";
	}
	return s;
    }

}
