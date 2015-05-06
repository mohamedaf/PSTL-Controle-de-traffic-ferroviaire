package trafic.ruler;

import java.util.ArrayList;

import trafic.elements.Light;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.elements.SwitchEdges;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.SensorType;
import trafic.enums.SwitchPos;
import trafic.enums.TrainAction;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;
import trafic.ruler.thread.ReUpThread;

/**
 * @author KOBROSLI - AFFES
 * 
 *         Regles du scenario 1
 */
public class RulerScen1 implements IRuler {
    private final static int numScen = 1;

    private IController controller;
    private Pcf circuit;
    private ReUpThread reUpThread;

    /**
     * Constructeur
     * 
     * @param controller
     *            : instance du controller
     */
    public RulerScen1(IController controller) {
	super();
	this.controller = controller;
	circuit = controller.getPCF();
	reUpThread = new ReUpThread(5000, this);
    }

    public RulerScen1() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyInit() {
	reUpThread.start();

	/* Recuperation de la liste des feux et positions */
	ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
		.getListLights().clone();

	ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
		.getListPositions().clone();

	int i, after, captNum;

	/* Tous les feux se trouvant derriere un train sont mis au rouge */
	for (Position p : listPos) {
	    /* Recuperer l'Id du feu devant le train courant */

	    after = p.getAfter().getId();

	    /*
	     * On doit recuperer la liste des capteurs in de ce feu afin de les
	     * mettre au rouge
	     */
	    for (SensorEdges sw : circuit.getTopography().getSensorEdgesList()) {
		if (sw.getCapteur().getId() == after) {
		    /* On a la liste des feux in */
		    for (Sensor sw2 : sw.getCapteurInList()) {

			captNum = sw2.getId();

			/* On met chaque feu au rouge */
			/* supprimer ce feu */
			for (i = 0; i < listLights.size(); i++) {
			    if (captNum == listLights.get(i).getId()) {
				listLights.remove(i);
				break;
			    }
			}
		    }
		}
	    }
	}

	for (SwitchEdges s : circuit.getTopography().getSwitchEdgesList()) {
	    /* on verifie si un train est positionne a l'aiguillage */
	    for (Position p : listPos) {
		if (p.getAfter().getId() != s.getBranch0()) {
		    /* pos a branch 0 */

		    controller.setSwitch(s.getId(), SwitchPos.b0);

		    /* On met le feu de la branch1 au rouge */
		    for (i = 0; i < listLights.size(); i++) {
			if (s.getBranch1() == listLights.get(i).getId()) {
			    listLights.remove(i);
			    break;
			}
		    }

		    break;
		} else if (p.getAfter().getId() != s.getBranch1()) {
		    /* pos a branch 1 */

		    controller.setSwitch(s.getId(), SwitchPos.b1);

		    /* On met le feu de la branch0 au rouge */
		    for (i = 0; i < listLights.size(); i++) {
			if (s.getBranch0() == listLights.get(i).getId()) {
			    listLights.remove(i);
			    break;
			}
		    }

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
		}
	    }
	}
    }

    @Override
    public void notifyUp(int sensorId) {
	Light myLight = null, myLightBefore = null;
	Position pos = null;
	int idAfter = -1, idBefore = -1;
	Train t = null, tBefore = null;

	/* On récupère le train qui vient d'activer le capteur */
	for (Position p : controller.getPCF().getInit().getListPositions()) {
	    if (p.getAfter().getId() == sensorId) {
		pos = p;
		t = p.getTrain();
		idAfter = pos.getAfter().getId();
		idBefore = pos.getBefore().getId();
	    }
	}

	/* Si le train est sur une station et est en marche, on l'arrête */
	if (controller.getPCF().getTopography()
		.getSensorEdgesById(pos.getBefore().getId()).getCapteur()
		.getType() == SensorType.station
		&& t.getAction() == TrainAction.start) {

	    controller.setTrain(t.getId(), TrainAction.stop, t.getDirection(),
		    false);
	    reUpThread.addReUp(sensorId);
	    return;
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

	/* Si le feu de devant est vert */
	if (myLight.getColor() == Color.green) {

	    /* SI le capteur de devant mene a un aiguillage */
	    if (controller.getPCF().getTopography().isPartOfSwitchEdge(idAfter)) {
		SwitchEdges switchEdges = controller.getPCF().getTopography()
			.getSwitchEdgesByBranch(idAfter);
		if (switchEdges.isBranch0(idAfter)
			&& switchEdges.getPos() == SwitchPos.b1) {

		    controller.setSwitch(switchEdges.getId(), SwitchPos.b0);
		} else if (switchEdges.isBranch1(idAfter)
			&& switchEdges.getPos() == SwitchPos.b0) {
		    controller.setSwitch(switchEdges.getId(), SwitchPos.b1);
		}

	    }

	    /* Si le capteur precedent est le trunk d'un aiguillage */
	    if (controller.getPCF().getTopography()
		    .isPartOfSwitchEdge(idBefore)) {
		SwitchEdges switchEdges = controller.getPCF().getTopography()
			.getSwitchEdgesByTrunk(idBefore);

		Train trainBranch0 = null, trainBranch1 = null;
		Light lightBranch0 = null, lightBranch1 = null;

		/* Si il y a un train sur la branche 0 de l'aiguillage precedent */
		if (controller.getPCF().getInit()
			.getPositionByAfterSensor(switchEdges.getBranch0()) != null) {

		    trainBranch0 = controller.getPCF().getInit()
			    .getPositionByAfterSensor(switchEdges.getBranch0())
			    .getTrain();
		    lightBranch0 = controller.getPCF().getLights()
			    .getLightById(switchEdges.getBranch0());
		}
		/*
		 * Si il y a un train sur la branche 1 de l'aiguillage precedent
		 */
		if (controller.getPCF().getInit()
			.getPositionByAfterSensor(switchEdges.getBranch1()) != null) {

		    trainBranch1 = controller.getPCF().getInit()
			    .getPositionByAfterSensor(switchEdges.getBranch1())
			    .getTrain();
		    lightBranch1 = controller.getPCF().getLights()
			    .getLightById(switchEdges.getBranch1());
		}
		/*
		 * Si l'aiguillage est en branche 0 on le met sur branche 1
		 * (pour alterner une fois sur 2, sinon on pourrait avoir tous
		 * les trains d'une branche qui passe avant ceux de l'autre
		 * branche)
		 */
		if (trainBranch0 != null && trainBranch1 == null) {
		    tBefore = trainBranch0;
		    myLightBefore = lightBranch0;
		} else if (trainBranch0 == null && trainBranch1 != null) {
		    tBefore = trainBranch1;
		    myLightBefore = lightBranch1;
		} else if (trainBranch0 != null && trainBranch1 != null) {
		    if (switchEdges.getPos() == SwitchPos.b0) {
			controller.setSwitch(switchEdges.getId(), SwitchPos.b1);
			tBefore = trainBranch1;
			myLightBefore = lightBranch1;
		    } else {
			controller.setSwitch(switchEdges.getId(), SwitchPos.b0);
			tBefore = trainBranch0;
			myLightBefore = lightBranch0;
		    }
		}

	    }

	    /* On démarre le train */
	    controller.setTrain(t.getId(), TrainAction.start, t.getDirection(),
		    false);
	    /* Le train vient de passer, on met le feu au rouge */
	    controller.setLight(myLight.getId(), Color.red);

	    /* Regles 4 et 3 */
	    controller.setLight(myLightBefore.getId(), Color.green);

	    /* Si il y a un train derriere qui est a l'arret, up */

	    if (tBefore != null && tBefore.getAction() == TrainAction.stop) {
		notifyUp(myLightBefore.getId());
	    }
	} else {
	    controller.setTrain(t.getId(), TrainAction.stop, t.getDirection(),
		    false);
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
