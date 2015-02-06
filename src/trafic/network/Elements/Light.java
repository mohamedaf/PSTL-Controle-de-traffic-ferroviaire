package trafic.network.Elements;

public class Light {

    private final int id;
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
}
