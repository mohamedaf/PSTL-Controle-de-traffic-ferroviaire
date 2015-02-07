package trafic.network.Elements;

import java.util.ArrayList;

public class Topography {

	private ArrayList<SensorEdges> SensorEdgesList;

	public Topography() {
		SensorEdgesList = new ArrayList<SensorEdges>();
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

	public String toString() {
		String s = "";
		for (SensorEdges sel : SensorEdgesList) {
			s += sel.toString() + "\n";
		}
		return s;
	}

}
