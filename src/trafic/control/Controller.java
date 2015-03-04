package trafic.control;

import trafic.cparser.CParser;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.SensorEdges;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;
import trafic.interfaces.IUpNotifier;
import trafic.interfaces.StartableStoppable;

public class Controller implements IController, IUpNotifier, StartableStoppable {
    IRuler ruler;
    CParser parser;
    Pcf circuit;

    public Controller(IRuler ruler) {
	this.ruler = ruler;

    }

    @Override
    public void setTrain(int id, TrainAction action, TrainDirection direction,
	    boolean init) {
	Position p = circuit.getInit().getPositionByTrainId(id);
	Train t = p.getTrain();

	/*
	 * Si l'action demand�e est diff�rente de l'�tat actuel du train, au
	 * envoie une requete au serveur
	 */

	if (action != t.getAction() || direction != t.getDirection())
	    parser.setTrainToXml(id, action, direction);

	t.setAction(action);
	t.setDirection(direction);
	if (action == TrainAction.start) {
	    if (!init) {

		p.setBefore(p.getAfter());
		/*
		 * mise a jour de la position du train car dans le cas
		 * NotifyInit on a tojours pas reçu de reponse up on demande
		 * juste aux trains de demarrer par contre apres le cas Init la
		 * c'est plus pareil les trains roulent donc on effecture le
		 * changement de position
		 */

		for (SensorEdges se : circuit.getTopography()
			.getSensorEdgesList()) {
		    if (se.getCapteur().getId() == p.getAfter().getId()) {
			p.setAfter(se.getCapteurOut());
			break;
		    }
		}
		System.out.println("Train : " + t.getId() + " before : "
			+ p.getBefore() + "after : " + p.getAfter());
	    }
	}
	/*
	 * System.out.println("next train : " + id + " capt : " +
	 * p.getAfter().getId()); System.out.println("setTrain id: " + id +
	 * " action: " + action + " direction: " + direction);
	 */
    }

    @Override
    public void setLight(int id, Color color) {
	parser.setLightToXml(id, color);
	circuit.getLights().getLightById(id).setColor(color);
	System.out.println("setLight id: " + id + " color: " + color);
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

    @Override
    public void start() {
	parser = new CParser(this);
	ruler.setController(this);

	parser.helloToXml(1);
	parser.startToXml();
	try {
	    Thread.sleep(2000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	ruler.notifyInit();

    }

    @Override
    public void stop() {
	parser.byeToXml();
    }

    @Override
    public void setSwitch(int id, SwitchPos pos) {
	parser.setSwitchToXml(id, pos);

    }

}
