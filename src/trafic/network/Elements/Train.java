package trafic.network.Elements;

public class Train {

    private final int id;
    private TrainAction action;
    private TrainDirection direction;

    public Train(int id, TrainAction action, TrainDirection direction) {
	this.id = id;
	this.action = action;
	this.direction = direction;
    }

    public int getId() {
	return id;
    }

    public TrainAction getAction() {
	return action;
    }

    public void setAction(TrainAction action) {
	this.action = action;
    }

    public TrainDirection getDirection() {
	return direction;
    }

    public void setDirection(TrainDirection direction) {
	this.direction = direction;
    }
}
