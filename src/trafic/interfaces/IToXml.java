package trafic.interfaces;

import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IToXml {

    /**
     * Envoie une commande set train au format XML au moniteur (serveur)
     * 
     * @param id
     *            : identifiant du train
     * @param action
     *            : nouvelle action du train (start | stop)
     * @param direction
     *            : nouvelle direction du train
     */
    public void setTrainToXml(int id, TrainAction action,
	    TrainDirection direction);

    /**
     * Envoie une commande set light au format XML au moniteur (serveur)
     * 
     * @param id
     *            : identifiant du feu
     * @param color
     *            : nouvelle couleur du feu
     */
    public void setLightToXml(int id, Color color);

    /**
     * Envoie une commande hello au format XML au moniteur (serveur)
     * 
     * @param id
     *            : identifiant de la balise
     */
    public void helloToXml(int id);

    /**
     * Envoie une commande start au format XML au moniteur (serveur)
     */
    public void startToXml();

    /**
     * Envoie une commande bye au format XML au moniteur (serveur)
     */
    public void byeToXml();

    /**
     * Envoie une commande info au format XML au moniteur (serveur)
     * 
     * @param status
     *            : (ok | ko)
     * @param message
     *            : message contenant l'information a transmettre
     */
    public void infoToXml(Status status, String message);

    /**
     * Envoie une commande Switch au format XML au moniteur (serveur)
     * 
     * @param id
     *            : identifiant de la balise switch-Edges concernee
     * @param pos
     *            : nouvelle position de la branche
     */
    public void setSwitchToXml(int id, SwitchPos pos);

}
