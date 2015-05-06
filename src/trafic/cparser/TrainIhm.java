package trafic.cparser;

import java.awt.Color;

import trafic.IHM.Lumiere;
import trafic.elements.Position;

public class TrainIhm {
    private Lumiere lum;
    private Position pos;
    private boolean entreCapteurFeu;
    private int size;

    public TrainIhm(int x, int y, Position p, Color colorOn, Color colorOff,
	    int size) {
	super();
	this.pos = p;
	this.entreCapteurFeu = false;
	this.lum = new Lumiere(colorOn, colorOff, x, y, size, size);
	this.size = size;
    }

    public TrainIhm(Lumiere lum, Position p, int size) {
	this.lum = lum;
	this.pos = p;
	this.entreCapteurFeu = false;
	this.size = size;
    }

    public int getSize() {
	return size;
    }

    public int getY() {
	return lum.getY();
    }

    public void setY(int y) {
	this.lum.setY(y);
    }

    public int getX() {
	return this.lum.getX();
    }

    public void setX(int x) {
	this.lum.setX(x);
    }

    public Position getPos() {
	return pos;
    }

    public Color getColor() {
	return lum.getColor();
    }

    public void switchColor() {
	if (lum.isOn())
	    lum.setColor(lum.getColorOff());
	else
	    lum.setColor(lum.getColorOn());
    }

    public void setPos(Position pos) {
	this.pos = pos;
    }

    public boolean isEntreCapteurFeu() {
	return entreCapteurFeu;
    }

    public void setEntreCapteurFeu(boolean entreCapteurFeu) {
	this.entreCapteurFeu = entreCapteurFeu;
    }

}