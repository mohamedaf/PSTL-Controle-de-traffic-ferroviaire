package trafic.cparser;

public class TrainIhm {
    private double angle;
    private int x, y;

    public TrainIhm(double angle, int x, int y) {
	super();
	this.angle = angle;
	this.x = x;
	this.y = y;
    }

    public double getAngle() {
	return angle;
    }

    public void setAngle(double angle) {
	this.angle = angle;
    }

    public int getY() {
	return y;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public void setX(int x) {
	this.x = x;
    }

}