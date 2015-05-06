package trafic.IHM;

import java.awt.Color;

import trafic.elements.Position;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant un train, sert a l'affichage du train sur l'IHM
 */
public class TrainIhm {
    private Lumiere lum;
    private Position pos;
    private boolean entreCapteurFeu;
    private int size;

    /**
     * Constructeur
     * 
     * @param x
     *            : coordonnee x du train
     * @param y
     *            : coordonnee y du train
     * @param p
     *            : l'objet representant la balise position contenant le train
     * @param colorOn
     *            :
     * @param colorOff
     *            :
     * @param size
     *            :
     */
    public TrainIhm(int x, int y, Position p, Color colorOn, Color colorOff,
	    int size) {
	super();
	this.pos = p;
	this.entreCapteurFeu = false;
	this.lum = new Lumiere(colorOn, colorOff, x, y, size, size);
	this.size = size;
    }

    /**
     * Constructeur
     * 
     * @param lum
     * @param p
     * @param size
     */
    public TrainIhm(Lumiere lum, Position p, int size) {
	this.lum = lum;
	this.pos = p;
	this.entreCapteurFeu = false;
	this.size = size;
    }

    /**
     * 
     * @return
     */
    public int getSize() {
	return size;
    }

    /**
     * 
     * @return
     */
    public int getY() {
	return lum.getY();
    }

    /**
     * 
     * @param y
     */
    public void setY(int y) {
	this.lum.setY(y);
    }

    /**
     * 
     * @return
     */
    public int getX() {
	return this.lum.getX();
    }

    /**
     * 
     * @param x
     */
    public void setX(int x) {
	this.lum.setX(x);
    }

    /**
     * 
     * @return
     */
    public Position getPos() {
	return pos;
    }

    /**
     * 
     * @return
     */
    public Color getColor() {
	return lum.getColor();
    }

    /**
     * 
     */
    public void switchColor() {
	if (lum.isOn())
	    lum.setColor(lum.getColorOff());
	else
	    lum.setColor(lum.getColorOn());
    }

    /**
     * 
     * @param pos
     */
    public void setPos(Position pos) {
	this.pos = pos;
    }

    /**
     * 
     * @return
     */
    public boolean isEntreCapteurFeu() {
	return entreCapteurFeu;
    }

    /**
     * 
     * @param entreCapteurFeu
     */
    public void setEntreCapteurFeu(boolean entreCapteurFeu) {
	this.entreCapteurFeu = entreCapteurFeu;
    }

}