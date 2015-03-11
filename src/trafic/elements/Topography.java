package trafic.elements;

import java.util.ArrayList;

public class Topography {

    private ArrayList<SensorEdges> sensorEdgesList;
    private ArrayList<SwitchEdges> switchEdgesList;

    public Topography() {
	sensorEdgesList = new ArrayList<SensorEdges>();
	switchEdgesList = new ArrayList<SwitchEdges>();
    }

    public void addSwitchEdges(SwitchEdges s) {
	switchEdgesList.add(s);
    }

    public SwitchEdges getSwitchEdges(int i) {
	return switchEdgesList.get(i);
    }

    public SwitchEdges getSwitchEdgesById(int id) {
	for (SwitchEdges se : switchEdgesList) {
	    if (se.getId() == id) {
		return se;
	    }
	}
	return null;
    }

    public ArrayList<SwitchEdges> getSwitchEdgesList() {
	return switchEdgesList;
    }

    public void addSensorEdges(SensorEdges s) {
	sensorEdgesList.add(s);
    }

    public SensorEdges getSensorEdges(int i) {
	return sensorEdgesList.get(i);
    }

    public ArrayList<SensorEdges> getSensorEdgesList() {
	return sensorEdgesList;
    }

    public SensorEdges getSensorEdgesById(int sensorId) {
	for (SensorEdges se : sensorEdgesList) {
	    if (se.getCapteur().getId() == sensorId) {
		return se;
	    }
	}
	return null;
    }

    public boolean isPartOfSwitchEdge(int id) {
	for (SwitchEdges se : switchEdgesList) {
	    if (id == se.getTrunk() || id == se.getBranch0()
		    || id == se.getBranch1())
		return true;
	}
	return false;
    }

    public SwitchEdges getSwitchEdgesByBranch(int branchId) {
	for (SwitchEdges se : switchEdgesList) {
	    if (se.isBranch0(branchId) || se.isBranch1(branchId))
		return se;
	}
	return null;
    }

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
