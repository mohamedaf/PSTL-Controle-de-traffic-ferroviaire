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
