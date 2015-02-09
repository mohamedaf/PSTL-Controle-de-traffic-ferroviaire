package trafic.control;

import trafic.Enum.Color;
import trafic.Enum.TrainAction;
import trafic.Enum.TrainDirection;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IControleur;
import trafic.network.SocketCommunicator;

public class Controleur implements IControleur {
	ICommunicator communicator;
	
	public Controleur(){
		this.communicator = new SocketCommunicator();
	}

	@Override
	public void setTrain(int id, TrainAction action, TrainDirection direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLight(int id, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

}
