package trafic.cparser.toxml;

import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IToXml;
import trafic.test.SocketCommunicatorTest;

public class ToXml implements IToXml {
	ICommunicator communicator;

	public ToXml(ICommunicator communicator) {
		this.communicator = communicator;
	}

	@Override
	public void setTrainToXml(int id, TrainAction action,
			TrainDirection direction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLightToXml(int id, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void helloToXml(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startToXml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void byeToXml() {
		// TODO Auto-generated method stub

	}

	@Override
	public void infoToXml(Status status, String message) {
		// TODO Auto-generated method stub

	}

}
