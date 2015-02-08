package trafic.network.Elements;

import trafic.network.Enum.Color;

public class Light {

    private int id;
    private Color color;

    public Light(int id, Color color) {
	this.id = id;
	this.color = color;
    }

    public int getId() {
	return id;
    }

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    public String toString() {
	return "Light : " + id + " color : " + color;
    }
}
