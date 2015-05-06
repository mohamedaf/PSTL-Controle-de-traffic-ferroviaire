package trafic.elements;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Scenario
 */
public class Scenario {

    private final int id;

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant du scenario
     */
    public Scenario(int id) {
	this.id = id;
    }

    /**
     *
     * @return id du scenario
     */
    public int getId() {
	return id;
    }

    @Override
    public String toString() {
	return "Scenario " + id;
    }
}
