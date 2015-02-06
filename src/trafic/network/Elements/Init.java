package trafic.network.Elements;

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

}
