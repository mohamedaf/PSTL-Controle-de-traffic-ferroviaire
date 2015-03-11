package trafic.elements;

import java.util.ArrayList;

public class Topography {

    private ArrayList<SensorEdges> SensorEdgesList;
    private ArrayList<SwitchEdges> SwitchEdgesList;

    public Topography() {
	SensorEdgesList = new ArrayList<SensorEdges>();
	SwitchEdgesList = new ArrayList<SwitchEdges>();
    }

    public void addSwitchEdges(SwitchEdges s) {
	SwitchEdgesList.add(s);
    }

    public SwitchEdges getSwitchEdges(int i) {
	return SwitchEdgesList.get(i);
    }

    public SwitchEdges getSwitchEdgesById(int id) {
	for (SwitchEdges se : SwitchEdgesList) {
	    if (se.getId() == id) {
		return se;
	    }
	}
	return null;
    }

    public ArrayList<SwitchEdges> getSwitchEdgesList() {
	return SwitchEdgesList;
    }

    public void addSensorEdges(SensorEdges s) {
	SensorEdgesList.add(s);
    }

    public SensorEdges getSensorEdges(int i) {
	return SensorEdgesList.get(i);
    }

    public ArrayList<SensorEdges> getSensorEdgesList() {
	return SensorEdgesList;
    }

    public boolean isPartOfSwitchEdge(int id) {
	for (SwitchEdges se : SwitchEdgesList) {
	    if (id == se.getTrunk() || id == se.getBranch0()
		    || id == se.getBranch1())
		return true;
	}
	return false;
    }

    public SwitchEdges getSwitchEdgesByBranch(int branchId) {
	for (SwitchEdges se : SwitchEdgesList) {
	    if (se.isBranch0(branchId) || se.isBranch1(branchId))
		return se;
	}
	return null;
    }

    public SwitchEdges getSwitchEdgesByTrunk(int trunkId) {
	for (SwitchEdges se : SwitchEdgesList) {
	    if (se.isTrunk(trunkId))
		return se;
	}
	return null;
    }

    @Override
    public String toString() {
	String s = "";
	for (SensorEdges sel : SensorEdgesList) {
	    s += sel.toString() + "\n";
	}
	for (SwitchEdges sel : SwitchEdgesList) {
	    s += sel.toString() + "\n";
	}
	return s;
    }

}
