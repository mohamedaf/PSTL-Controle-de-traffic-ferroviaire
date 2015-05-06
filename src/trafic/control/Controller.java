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
import trafic.interfaces.IIhm;
import trafic.interfaces.IRuler;
import trafic.interfaces.IUpNotifier;
import trafic.interfaces.StartableStoppable;

/**
 * 
 * @author KOBROSLI - AFFES
 * 
 *         Classe principale servant de lien entre les trois composants : Ruler,
 *         Parser et IHM. Elle reçoit les requetes des composants Ruler et IHM
 *         puis les executent en faisant appel au composant Parser permettant
 *         l'envois des requetes vers le serveur
 */
public class Controller implements IController, IUpNotifier, StartableStoppable {
    IRuler ruler;
    CParser parser;
    Pcf circuit;
    IIhm ihm;

    boolean randomOneTwoSwitch = true;
    boolean automaticSwitch = true;

    /**
     * Constructeur
     * 
     * @param ruler
     *            : Un scenario choisi parmi trois disponibles.
     */
    public Controller(IRuler ruler) {
	this.ruler = ruler;
    }

    /**
     * Modification ou initialisation de l'IHM
     * 
     * @param ihm
     *            : nouvelle IHM
     */
    public void setIhm(IIhm ihm) {
	this.ihm = ihm;
    }

    @Override
    public void setTrain(int id, TrainAction action, TrainDirection direction,
	    boolean init) {
	Position p = circuit.getInit().getPositionByTrainId(id);
	Train t = p.getTrain();

	System.out.println("Set train " + t.getId() + " " + action);

	/*
	 * Si l'action demandee est differente de l'etat actuel du train, au
	 * envoie une requete au serveur
	 */

	if (action != t.getAction() || direction != t.getDirection())
	    parser.setTrainToXml(id, action, direction);

	t.setAction(action);
	t.setDirection(direction);
	if (action == TrainAction.start) {
	    if (!init) {
		if (ihm != null)
		    ihm.step(id);

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
    }

    @Override
    public void setLight(int id, Color color) {
	if (circuit.getLights().getLightById(id).getColor() != color) {
	    if (ihm != null)
		ihm.switchLight(id);

	    parser.setLightToXml(id, color);
	    circuit.getLights().getLightById(id).setColor(color);
	    System.out.println("setLight id: " + id + " color: " + color);

	}
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

	if (ihm != null)
	    ihm.notifyInit(circuit);

	ruler.notifyInit();
    }

    @Override
    public void notifyUp(int sensorId) {
	if (ihm != null)
	    ihm.notifyUp(sensorId);

	ruler.notifyUp(sensorId);
    }

    @Override
    public void start() {
	parser = new CParser(this);
	ruler.setController(this);

	parser.helloToXml(1);
	parser.startToXml();
    }

    @Override
    public void stop() {
	parser.byeToXml();
    }

    @Override
    public void setSwitch(int id, SwitchPos pos) {
	System.out.println("set switch " + id + " to pos " + pos);
	circuit.getTopography().getSwitchEdgesById(id).setPos(pos);
	parser.setSwitchToXml(id, pos);

    }

    @Override
    public boolean isRandomOneTwoSwitch() {
	return randomOneTwoSwitch;
    }

    /**
     * Affecter une nouvelle valeur au booleen randomOneTwoSwitch
     * 
     * @param randomOneTwoSwitch
     *            : nouvelle valeur de la variable
     */
    public void setRandomOneTwoSwitch(boolean randomOneTwoSwitch) {
	this.randomOneTwoSwitch = randomOneTwoSwitch;
    }

    @Override
    public boolean isAutomaticSwitch() {
	return automaticSwitch;
    }

    /**
     * Affecter une nouvelle valeur au booleen automaticSwitch
     * 
     * @param automaticSwitch
     *            : nouvelle valeur de la variable
     */
    public void setAutomaticSwitch(boolean automaticSwitch) {
	this.automaticSwitch = automaticSwitch;
    }

}
