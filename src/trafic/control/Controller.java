package trafic.control;

import trafic.cparser.CParser;
import trafic.elements.Pcf;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.IController;
import trafic.interfaces.IUpNotifier;

public class Controller implements IController {
	IUpNotifier ruler;
	CParser parser;
	Pcf circuit;
  

	public Controller(IUpNotifier ruler) {
		this.ruler = ruler;
		parser = new CParser(this);
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

	@Override
	public Pcf getPCF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPCF() {
		// TODO Auto-generated method stub
		
	}

}
