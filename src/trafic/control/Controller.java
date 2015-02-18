package trafic.control;

import trafic.cparser.CParser;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.SensorEdges;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.IController;
import trafic.interfaces.IUpNotifier;

public class Controller implements IController, IUpNotifier {
    IUpNotifier ruler;
    CParser parser;
    Pcf circuit;

    public Controller(IUpNotifier ruler) {
	this.ruler = ruler;
	parser = new CParser(this);

	parser.helloToXml(1);
	parser.startToXml();
	ruler.notifyInit();
    }

    @Override
    public void setTrain(int id, TrainAction action, TrainDirection direction) {
	parser.setTrainToXml(id, action, direction);
	Position p = circuit.getInit().getPositionByTrainId(id);
	Train t = p.getTrain();
	t.setAction(action);
	t.setDirection(direction);
	if (action == TrainAction.start) {
	    p.setBefore(p.getAfter());

	    /* mise a jour de la position du train */

	    for (SensorEdges se : circuit.getTopography().getSensorEdgesList()) {
		if (se.getCapteur().getId() == p.getAfter().getId()) {
		    p.setAfter(se.getCapteurOut());
		}
	    }
	}
    }

    @Override
    public void setLight(int id, Color color) {
	parser.setLightToXml(id, color);
	circuit.getLights().getLightById(id).setColor(color);
    }

    @Override
    public Pcf getPCF() {
	return circuit;
    }

    @Override
    public void setPCF(Pcf circuit) {
	this.circuit = circuit;

    }

    @Override
    public void notifyInit() {
	ruler.notifyInit();

    }

    @Override
    public void notifyUp(int sensorId) {
	ruler.notifyUp(sensorId);

    }

}
