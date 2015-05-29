package trafic.IHM.elems;

public class PositionForIhm {
    private final int x, y;
    private final double angle;
    private final int before, after;
    private final boolean entreCapteurEtFeu;

    public PositionForIhm(int x, int y, double angle, int before, int after,
	    boolean entreCapteurEtFeu) {
	this.x = x;
	this.y = y;
	this.angle = angle;
	this.before = before;
	this.after = after;
	this.entreCapteurEtFeu = entreCapteurEtFeu;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public double getAngle() {
	return angle;
    }

    public int getBefore() {
	return before;
    }

    public int getAfter() {
	return after;
    }

    public boolean isEntreCapteurEtFeu() {
	return entreCapteurEtFeu;
    }

}
