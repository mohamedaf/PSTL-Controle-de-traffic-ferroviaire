package trafic.interfaces;

import trafic.elements.Pcf;

/**
 * @author KOBROSLI - AFFES
 *
 *         Interface de l'IHM
 */
public interface IIhm {

	/**
	 * Notifie l'IHM que le circuit est initialise
	 * 
	 * @param circuit
	 *            Circuit d'initialisation
	 */
	public void notifyInit(Pcf circuit);

	/**
	 * Change la couleur d'un feu
	 * 
	 * @param lightId
	 *            Id du feu dont on change la couleur
	 */
	public void switchLight(int lightId);

	/**
	 * Notifie d'un up du capteur
	 * 
	 * @param sensorId
	 *            Capteur active
	 */
	public void notifyUp(int sensorId);

	/**
	 * Indique que le train a avance d'un pas
	 * 
	 * @param idTrain
	 *            Id du train qui avance
	 */
	public void step(int idTrain);

}
