package trafic.cparser;

import trafic.cparser.parser.Parser;
import trafic.cparser.toxml.ToXml;
import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IController;
import trafic.interfaces.IParser;
import trafic.interfaces.IToXml;
import trafic.network.SocketCommunicator;

public class CParser implements IParser, IToXml {
	private IParser parser;
	private IToXml toXml;
	private ICommunicator communicator;

	public CParser(IController controller) {
		parser = new Parser(controller);
		communicator = new SocketCommunicator(parser);
		toXml = new ToXml(communicator);

	}

	@Override
	public void setTrainToXml(int id, TrainAction action,
			TrainDirection direction) {
		toXml.setTrainToXml(id, action, direction);

	}

	@Override
	public void setLightToXml(int id, Color color) {
		toXml.setLightToXml(id, color);

	}

	@Override
	public void helloToXml(int id) {
		toXml.helloToXml(id);

	}

	@Override
	public void startToXml() {
		toXml.startToXml();

	}

	@Override
	public void byeToXml() {
		toXml.byeToXml();

	}

	@Override
	public void infoToXml(Status status, String message) {
		toXml.infoToXml(status, message);

	}

	@Override
	public void parse(String xml) {
		parser.parse(xml);

	}

}
