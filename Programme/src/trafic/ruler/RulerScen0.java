package trafic.ruler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import trafic.elements.Light;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.TrainAction;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;

/**
 * @author KOBROSLI - AFFES
 * 
 *         Regles du scenario 0
 */
public class RulerScen0 implements IRuler {
    private final static int numScen = 0;

    private IController controller;
    private Pcf circuit;
    private HashMap<Integer, Boolean> initList;

    /**
     * Constructeur
     * 
     * @param controller
     *            : instance du controller
     */
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

	for (Map.Entry<Integer, Boolean> entry : initList.entrySet()) {
	    System.out.println(entry.getKey() + " " + entry.getValue());
	}

	/* Politique de Securite */

	/* Regles 1 et 2 */

	/* On recupere le train qui vient d'activer le capteur */
	for (Position p : controller.getPCF().getInit().getListPositions()) {
	    if (p.getAfter().getId() == sensorId) {
		pos = p;
		t = p.getTrain();
	    }
	}

	PrintStream pr = new PrintStream(System.out);

	if (pos == null) {
	    pr.print("nuuuuuuuuuuul\n");
	    pr.flush();
	}

	/* on recupere le train precedent */
	for (Position p : controller.getPCF().getInit().getListPositions()) {
	    if (p.getAfter().getId() == pos.getBefore().getId()) {
		tBefore = p.getTrain();
	    }
	}

	/*
	 * on recupere le feu qui correspond au capteur active et le feu
	 * precedent
	 */
	for (Light l : circuit.getLights().getListLights()) {
	    if (l.getId() == sensorId) {
		myLight = l;
	    }
	    if (l.getId() == pos.getBefore().getId()) {
		myLightBefore = l;
	    }
	}

	if (myLight.getColor() == Color.green) {
	    /* Si le feu est vert on demarre le train */
	    controller.setTrain(t.getId(), TrainAction.start, t.getDirection(),
		    initList.get(t.getId()));

	    if (initList.get(t.getId()) == true) {
		initList.put(t.getId(), false);
	    } else {
		/* On met le feu au rouge apres le passage du train */
		controller.setLight(myLight.getId(), Color.red);

		/* Regles 4 et 3 */
		/* On met le feu se trouvant avant le train au vert */
		controller.setLight(myLightBefore.getId(), Color.green);

		/* Si il y a un train derriere qui est a l'arret, up */
		if (tBefore != null && tBefore.getAction() == TrainAction.stop) {
		    notifyUp(myLightBefore.getId());
		}
	    }
	} else {
	    /* Sinon on arrete le train car le feu est rouge */
	    controller.setTrain(t.getId(), TrainAction.stop, t.getDirection(),
		    false);
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
	/* Recuperation de la liste des feux */
	ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
		.getListLights().clone();

	/* Recuperation de la liste des positions */
	ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
		.getListPositions().clone();

	initList = new HashMap<Integer, Boolean>();

	for (Position p : listPos) {
	    /* Recuperer l'Id du feu derriere le train courant */
	    int captNum = p.getBefore().getId();

	    /* Init true */
	    initList.put(p.getTrain().getId(), true);

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
			    TrainAction.start, p.getTrain().getDirection(),
			    true);

		    initList.put(p.getTrain().getId(), false);
		}
	    }
	}
    }

    @Override
    public void setController(IController cont) {
	this.controller = cont;
	circuit = controller.getPCF();
    }

    @Override
    public int getNumScen() {
	return numScen;
    }
}
