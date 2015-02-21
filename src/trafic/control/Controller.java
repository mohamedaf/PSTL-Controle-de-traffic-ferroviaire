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
import trafic.interfaces.IRuler;
import trafic.interfaces.IUpNotifier;

public class Controller implements IController, IUpNotifier {
	IUpNotifier ruler;
	CParser parser;
	Pcf circuit;

	public Controller(IRuler ruler) {
		this.ruler = ruler;
		parser = new CParser(this);
		ruler.setController(this);

		parser.helloToXml(1);
		parser.startToXml();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println("setTrain id: "+id+" action: "+action+" direction: "+direction);
	}

	@Override
	public void setLight(int id, Color color) {
		parser.setLightToXml(id, color);
		circuit.getLights().getLightById(id).setColor(color);
		System.out.println("setLight id: "+id+" color: "+color);
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
