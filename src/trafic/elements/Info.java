package trafic.elements;

import trafic.enums.Status;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Info
 */
public class Info {

    private Status status;
    private String chaine;

    /**
     * Constructeur
     * 
     * @param status
     *            : (ok | ko)
     * @param chaine
     *            : contenu de l'information
     */
    public Info(Status status, String chaine) {
	this.status = status;
	this.chaine = chaine;
    }

    /**
     * 
     * @return status (ok | ko)
     */
    public Status getStatus() {
	return status;
    }

    /**
     * Modifie le champ status
     * 
     * @param status
     *            : nouveau status
     */
    public void setStatus(Status status) {
	this.status = status;
    }

    /**
     * 
     * @return chaine du message
     */
    public String getChaine() {
	return chaine;
    }

    /**
     * Modifie le champ chaine
     * 
     * @param chaine
     *            : nouvelle chaine
     */
    public void setChaine(String chaine) {
	this.chaine = chaine;
    }
}
