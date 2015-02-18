package trafic.elements;

import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

public class Position {

    private Sensor before;
    private Sensor after;
    private Train train;

    public Position(Sensor before, Sensor after, Train train) {
	this.before = before;
	this.after = after;
	this.train = train;
    }

    public Sensor getBefore() {
	return before;
    }

    public void setBefore(Sensor before) {
	this.before = before;
    }

    public Sensor getAfter() {
	return after;
    }

    public void setAfter(Sensor after) {
	this.after = after;
    }

    public Train getTrain() {
	return train;
    }

    public void setTrain(Train train) {
	this.train = train;
    }

    public void setTrainDirection(TrainDirection dir) {
	train.setDirection(dir);
    }

    public void setTrainAction(TrainAction act) {
	train.setAction(act);
    }

    @Override
    public String toString() {
	return train.toString() + " Avant : " + before + " Apres : " + after;
    }

}
