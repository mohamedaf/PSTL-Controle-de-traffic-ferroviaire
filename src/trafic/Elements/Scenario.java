package trafic.Elements;

public class Scenario {

    private final int id;

    public Scenario(int id) {
	this.id = id;
    }

    public int getId() {
	return id;
    }
    
    public String toString(){
    	return "Scenario "+id;
    }
}
