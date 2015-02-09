package trafic.elements;

public class Position {

	private Capteur before;
	private Capteur after;
	private Train train;

	public Position(Capteur before, Capteur after, Train train) {
		this.before = before;
		this.after = after;
		this.train = train;
	}

	public Capteur getBefore() {
		return before;
	}

	public void setBefore(Capteur before) {
		this.before = before;
	}

	public Capteur getAfter() {
		return after;
	}

	public void setAfter(Capteur after) {
		this.after = after;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public String toString() {
		return train.toString() + " Avant : " + before + " Apres : " + after;
	}

}
