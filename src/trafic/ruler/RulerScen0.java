package trafic.ruler;

import java.util.ArrayList;

import trafic.elements.Light;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.interfaces.IController;
import trafic.interfaces.IUpNotifier;

public class RulerScen0 implements IUpNotifier {
    private IController controller;
    private Pcf circuit;

    public RulerScen0(IController controller) {
	super();
	this.controller = controller;
	circuit = controller.getPCF();
    }

    public RulerScen0() {

    }

    @Override
    public void notifyUp(int sensorId) {
	Light myLight = null;
	Light myLightBefore = null;
	Position pos = null;
	Train t = null;
	Train tBefore = null;

	/* Politique de Securite */

	/* Regles 1 et 2 */

	for (Position p : controller.getPCF().getInit().getListPositions()) {
	    if (p.getAfter().getId() == sensorId) {
		pos = p;
		t = p.getTrain();
	    }
	}

	for (Position p : controller.getPCF().getInit().getListPositions()) {
	    if (p.getAfter().getId() == pos.getBefore().getId()) {
		tBefore = p.getTrain();
	    }
	}

	for (Light l : circuit.getLights().getListLights()) {
	    if (l.getId() == sensorId) {
		myLight = l;
	    }
	    if (l.getId() == pos.getBefore().getId()) {
		myLightBefore = l;
	    }
	}

	if (myLight.getColor() == Color.green) {
	    controller.setLight(myLight.getId(), Color.red);

	    /* Regles 4 et 3 */
	    controller.setLight(myLightBefore.getId(), Color.green);

	    if (tBefore != null) {
		controller.setTrain(tBefore.getId(), TrainAction.start,
			t.getDirection());
		controller.setLight(myLightBefore.getId(), Color.red);
	    }
	} else {
	    controller.setTrain(t.getId(), TrainAction.stop, t.getDirection());
	}

    }

    /*
     * L'etape d'Initialisation du Scenario 0. Tous les feux se trouvant
     * derriere les vehicules sont posiyionnes au rouge. Les autres sont
     * positionnes au vert. On demarre l'ensemble des vehicules.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void notifyInit() {
	/* Recuperation de la liste des feux et positions */
	ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
		.getListLights().clone();

	ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
		.getListPositions().clone();

	for (Position p : listPos) {
	    /* Recuperer l'Id du feu derriere le train courant */
	    int captNum = p.getBefore().getId();

	    /* supprimer ce feu */
	    for (int i = 0; i < listLights.size(); i++) {
		if (captNum == listLights.get(i).getId()) {
		    listLights.remove(i);
		    break;
		}
	    }
	}

	for (Light l : listLights) {
	    /* Mettre le reste des feux au vert */
	    controller.setLight(l.getId(), Color.green);

	    /* Demarrer les trains ayant un feu vert devant */
	    for (Position p : listPos) {
		if (p.getAfter().getId() == l.getId()) {
		    controller.setTrain(p.getTrain().getId(),
			    TrainAction.start, p.getTrain().getDirection());
		}
	    }
	}

    }

    public void setController(IController cont) {
	this.controller = cont;
	circuit = controller.getPCF();
    }
}
