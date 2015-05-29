package trafic.IHM.elems;

import java.awt.Color;

public class Lumiere implements LumiereInterface {
    private Color color, colorOn, colorOff;
    private int x, y;
    private int width, height;
    private int id;

    public Lumiere(Color colorOn, Color colorOff, int x, int y, int width,
	    int height, int id) {
	this.colorOn = colorOn;
	this.colorOff = colorOff;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.id = id;
	this.color = colorOff;
    }

    public Lumiere(Color colorOn, Color colorOff, int x, int y, int width,
	    int height) {
	this.colorOn = colorOn;
	this.colorOff = colorOff;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;

	this.color = colorOff;
	this.id = -1;
    }

    @Override
    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    @Override
    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

    @Override
    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    @Override
    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    @Override
    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    @Override
    public void on() {
	color = colorOn;

    }

    @Override
    public void off() {
	color = colorOff;

    }

    @Override
    public void setColorOn(Color c) {
	this.colorOn = c;

    }

    @Override
    public void setColorOff(Color c) {
	this.colorOff = c;

    }

    @Override
    public Color getColorOn() {
	return colorOn;
    }

    @Override
    public Color getColorOff() {
	// TODO Auto-generated method stub
	return colorOff;
    }

    @Override
    public boolean isOn() {
	return color == colorOn;
    }

    public int getId() {
	return id;
    }

}
