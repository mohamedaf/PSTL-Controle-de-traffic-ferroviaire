package trafic.elements;

import java.util.ArrayList;

public class Init {

    private ArrayList<Position> positions;

    public Init() {
	positions = new ArrayList<Position>();
    }

    public void addPosition(Position p) {
	positions.add(p);
    }

    public Position getPosition(int i) {
	return positions.get(i);
    }

    public ArrayList<Position> getListPositions() {
	return positions;
    }

    public Position getPositionByTrainId(int id) {
	for (Position p : positions) {
	    if (p.getTrain().getId() == id)
		return p;
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
