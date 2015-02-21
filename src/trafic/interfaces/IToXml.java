package trafic.interfaces;

import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

public interface IToXml {
	
	public void setTrainToXml(int id, TrainAction action, TrainDirection direction );
	
	public void setLightToXml(int id, Color color);
	
	public void helloToXml(int id);
	
	public void startToXml();
	
	public void byeToXml();
	
	public void infoToXml(Status status, String message);
	
	

}
