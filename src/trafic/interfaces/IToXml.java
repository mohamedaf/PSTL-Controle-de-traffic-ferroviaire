package trafic.interfaces;

import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * @author HK-Lab
 *
 */
public interface IToXml {
	
	/**
	 * Envoie  une commande set train au format pcf au serveur
	 * @param id
	 * @param action
	 * @param direction
	 */
	public void setTrainToXml(int id, TrainAction action, TrainDirection direction );
	
	/**
	 * Envoie  une commande set light au format pcf au serveur
	 * @param id
	 * @param color
	 */
	public void setLightToXml(int id, Color color);
	
	/**
	 * Envoie  une commande hello au format pcf au serveur
	 * @param id
	 */
	public void helloToXml(int id);
	
	/**
	 * Envoie  une commande start au format pcf au serveur
	 */
	public void startToXml();
	
	/**
	 * Envoie  une commande bye au format pcf au serveur
	 */
	public void byeToXml();
	
	/**
	 * Envoie  une commande info au format pcf au serveur
	 * @param status
	 * @param message
	 */
	public void infoToXml(Status status, String message);
	
	

}
