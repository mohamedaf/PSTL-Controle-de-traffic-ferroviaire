package trafic.elements;

import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Train
 */
public class Train {

    private final int id;
    private TrainAction action;
    private TrainDirection direction;

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant du train
     * @param action
     *            : (start | stop)
     * @param direction
     *            : (forward | backward)
     */
    public Train(int id, TrainAction action, TrainDirection direction) {
	this.id = id;
	this.action = action;
	this.direction = direction;
    }

    /**
     * Retourne l'identifiant du train
     * 
     * @return id
     */
    public int getId() {
	return id;
    }

    /**
     * Retourne (start | stop)
     * 
     * @return action
     */
    public TrainAction getAction() {
	return action;
    }

    /**
     * Modifie l'action du train
     * 
     * @param action
     *            : (start | stop)
     */
    public void setAction(TrainAction action) {
	this.action = action;
    }

    /**
     * Retourne (forward | backward)
     * 
     * @return direction
     */
    public TrainDirection getDirection() {
	return direction;
    }

    /**
     * Modifie la direction du train
     * 
     * @param direction
     *            : (forward | backward)
     */
    public void setDirection(TrainDirection direction) {
	this.direction = direction;
    }

    @Override
    public String toString() {
	return "Train " + id + " action : " + action + " direction "
		+ direction;
    }
}
