package trafic.control;

import trafic.elements.Pcf;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;
import trafic.parser.Parser;
import trafic.retransmit.Retransmit;

public class Controller implements IController {
    ICommunicator communicator;
    IRuler ruler;
    Parser parser;
    Pcf circuit;

    public Controller(IRuler ruler) {
	this.communicator = new Retransmit();
	this.ruler = ruler;
	parser = new Parser();
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
