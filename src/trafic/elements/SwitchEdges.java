package trafic.elements;

import trafic.enums.SwitchPos;
import trafic.enums.SwitchType;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise SwitchEdges
 */
public class SwitchEdges {

    private int id;
    private SwitchType type;
    private int trunk;
    private int branch0;
    private int branch1;
    private SwitchPos pos;

    public SwitchEdges() {

    }

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant de la balise switch-edges
     * @param type
     *            : (_1_2 | _2_1)
     * @param trunk
     *            : identifiant du capteur adjacent au tronc
     * @param branch0
     *            : identifiant du capteur adjacent a la branche 0
     * @param branch1
     *            : identifiant du capteur adjacent a la branche 1
     * @param pos
     *            : position actuelle de l'aiguillage (b0 | b1)
     */
    public SwitchEdges(int id, SwitchType type, int trunk, int branch0,
	    int branch1, SwitchPos pos) {
	super();
	this.id = id;
	this.type = type;
	this.trunk = trunk;
	this.branch0 = branch0;
	this.branch1 = branch1;
	this.pos = pos;
    }

    /**
     * Verifier si le capteur est adjacent au tronc
     * 
     * @param id
     *            : identifiant du capteur
     * @return True si capteur adjacent false sinon
     */
    public boolean isTrunk(int id) {
	return id == trunk;
    }

    /**
     * Verifier si le capteur est adjacent a la branche 0
     * 
     * @param id
     *            : identifiant du capteur
     * @return True si capteur adjacent false sinon
     */
    public boolean isBranch0(int id) {
	return id == branch0;
    }

    /**
     * Verifier si le capteur est adjacent a la branche 1
     * 
     * @param id
     *            : identifiant du capteur
     * @return True si capteur adjacent false sinon
     */
    public boolean isBranch1(int id) {
	return id == branch1;
    }

    /**
     * Retourne la position actuelle de l'aiguillage
     * 
     * @return pos
     */
    public SwitchPos getPos() {
	return pos;
    }

    /**
     * Modifie la position actuelle de l'aiguillage
     * 
     * @param pos
     *            : nouvelle position
     */
    public void setPos(SwitchPos pos) {
	this.pos = pos;
    }

    /**
     *
     * @return identifiant de la balise switch-edges
     */
    public int getId() {
	return id;
    }

    /**
     * Modifie l'identifiant de la balise switch-edges
     * 
     * @param id
     *            : nouvel identifiant de la balise switch-edges
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * Retourne le type d'aguillage (_1_2 | _2_1)
     * 
     * @return type
     */
    public SwitchType getType() {
	return type;
    }

    /**
     * Modifie le type de l'aiguillage
     * 
     * @param type
     *            : (_1_2 | _2_1)
     */
    public void setType(SwitchType type) {
	this.type = type;
    }

    /**
     * 
     * @return identifiant du capteur adjacent au tronc
     */
    public int getTrunk() {
	return trunk;
    }

    /**
     * Modifie l'identifiant capteur adjacent au tronc
     * 
     * @param trunk
     *            : nouvel identifiant du capteur
     */
    public void setTrunk(int trunk) {
	this.trunk = trunk;
    }

    /**
     * 
     * @return identifiant du capteur adjacent a la branche 0
     */
    public int getBranch0() {
	return branch0;
    }

    /**
     * Modifie l'identifiant du capteur adjacent a la branche 0
     * 
     * @param branch0
     *            : nouvel identifiant du capteur adjacent a la branche 0
     */
    public void setBranch0(int branch0) {
	this.branch0 = branch0;
    }

    /**
     * 
     * @return identifiant du capteur adjacent a la branche 1
     */
    public int getBranch1() {
	return branch1;
    }

    /**
     * Modifie l'identifiant du capteur adjacent a la branche 1
     * 
     * @param branch1
     *            : nouvel identifiant du capteur adjacent a la branche 1
     */
    public void setBranch1(int branch1) {
	this.branch1 = branch1;
    }
}
