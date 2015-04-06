package trafic.chemin;

public class SensorNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public SensorNotFoundException(int idCanton) {
		super("Canton n°" + idCanton + " not found.");
	}

}
