package trafic.interfaces;

import trafic.elements.Pcf;
import trafic.enums.Color;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IController {

    /**
     * Changer l'etat d'un train dans le circuit, cette methode modifie donc la
     * position et action du train selon les parametres action et direction et
     * envoie la requete demandant cette modification au moniteur(serveur)
     * 
     * @param id
     *            : identifiant du train
     * @param action
     *            : la nouvelle action du train
     * @param direction
     *            : la nouvelle direction du train
     * @param init
     *            : true si premier appel, false sinon
     */
    public void setTrain(int id, TrainAction action, TrainDirection direction,
	    boolean init);

    /**
     * Changer l'etat du feu ayant l'identifiant id
     * 
     * @param id
     *            : identifiant du feu
     * @param color
     *            : nouvelle couleur du feu
     */
    public void setLight(int id, Color color);

    /**
     * Retourne l'objet PCF contenant les informations de l'etat actuel du
     * circuit
     * 
     * @return circuit
     */
    public Pcf getPCF();

    /**
     * Modifie l'etat actuel du circuit
     * 
     * @param circuit
     *            : nouveau circuit
     */
    public void setPCF(Pcf circuit);

    /**
     * Peremet de modifier la position de la branche de l'aiguillage
     * d'identifiant id
     * 
     * @param id
     *            : identifiant de l'aiguillage
     * @param pos
     *            : nouvelle position de la branche de l'aiguillage
     */
    public void setSwitch(int id, SwitchPos pos);

    /**
     * Permet de savoir si la regle de changement de la branche d'aiguillage est
     * defini et non une alternance entre les deux voies
     * 
     * @return true si c'est le cas et false sinon
     */
    public boolean isAutomaticSwitch();

    /**
     * Permet de savoir si la regle de changement de la branche d'aiguillage est
     * defini et est une alternance entre les deux voies
     * 
     * @return true si alternance faux sinon
     */
    public boolean isRandomOneTwoSwitch();

}
