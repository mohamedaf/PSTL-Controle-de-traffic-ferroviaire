package trafic.elements;

import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Position
 */
public class Position {

    private Sensor before;
    private Sensor after;
    private Train train;

    /**
     * Constructeur
     * 
     * @param before
     *            : capteur se trouvant avant le train
     * @param after
     *            : capteur se trouvant apres le train
     * @param train
     *            : le train
     */
    public Position(Sensor before, Sensor after, Train train) {
	this.before = before.clone();
	this.after = after.clone();
	this.train = train;
    }

    /**
     * Retourne le capteur se trouvant avant le train
     * 
     * @return before
     */
    public Sensor getBefore() {
	return before;
    }

    /**
     * Modifie le capteur se trouvant avant le train
     * 
     * @param before
     *            : nouveau capteur
     */
    public void setBefore(Sensor before) {
	this.before = before.clone();
    }

    /**
     * Retourne le capteur se trouvant apres le train
     * 
     * @return after
     */
    public Sensor getAfter() {
	return after;
    }

    /**
     * Modifie le capteur se trouvant apres le train
     * 
     * @param after
     *            : nouveau capteur
     */
    public void setAfter(Sensor after) {
	this.after = after.clone();
    }

    /**
     * Retourne le train
     * 
     * @return train
     */
    public Train getTrain() {
	return train;
    }

    /**
     * Modifie le train
     * 
     * @param train
     *            : nouveau train
     */
    public void setTrain(Train train) {
	this.train = train;
    }

    /**
     * Modifier la direction du train (forward | backward)
     * 
     * @param dir
     *            : nouvelle direction du train
     */
    public void setTrainDirection(TrainDirection dir) {
	train.setDirection(dir);
    }

    /**
     * Modifier l'action du train (start | stop)
     * 
     * @param act
     *            : nouvelle action du train
     */
    public void setTrainAction(TrainAction act) {
	train.setAction(act);
    }

    @Override
    public String toString() {
	return train.toString() + " Avant : " + before + " Apres : " + after;
    }

}
