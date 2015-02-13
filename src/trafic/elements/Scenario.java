package trafic.elements;

public class Scenario {

    private final int id;

    public Scenario(int id) {
	this.id = id;
    }

    public int getId() {
	return id;
    }

    @Override
    public String toString() {
	return "Scenario " + id;
    }
}
