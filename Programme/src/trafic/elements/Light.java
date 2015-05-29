package trafic.elements;

import trafic.enums.Color;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Light
 */
public class Light {

    private int id;
    private Color color;

    /**
     * Constructeur
     * 
     * @param id
     *            : identifiant du feu
     * @param color
     *            : (green | red)
     */
    public Light(int id, Color color) {
	this.id = id;
	this.color = color;
    }

    /**
     * 
     * @return identifiant du feu
     */
    public int getId() {
	return id;
    }

    /**
     * 
     * @return couleur du feu (green | red)
     */
    public Color getColor() {
	return color;
    }

    /**
     * Modifie la couleur du feu
     * 
     * @param color
     *            : (green | red)
     */
    public void setColor(Color color) {
	this.color = color;
    }

    @Override
    public String toString() {
	return "Light : " + id + " color : " + color;
    }
}
