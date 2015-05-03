package trafic.chemin;

/**
 * 
 * @author KOBROSLI - AFFES
 * 
 *         Exception lancee l'ors de l'absence d'un Canton dans le circuit
 */
public class SensorNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * 
     * @param idCanton
     *            : identifiant du canton
     */
    public SensorNotFoundException(int idCanton) {
	super("Canton n°" + idCanton + " not found.");
    }

}
