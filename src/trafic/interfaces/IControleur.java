package trafic.interfaces;

import trafic.Enum.Color;
import trafic.Enum.TrainAction;
import trafic.Enum.TrainDirection;

public interface IControleur {

	public void setTrain(int id, TrainAction action, TrainDirection direction);

	public void setLight(int id, Color color);
	
	public void init();
	
	public void start();

}
