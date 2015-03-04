package trafic.ruler;

import java.util.ArrayList;

import trafic.elements.Light;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.elements.SwitchEdges;
import trafic.enums.SwitchPos;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;

public class RulerScen1 implements IRuler {
    private IController controller;
    private Pcf circuit;

    public RulerScen1(IController controller) {
	super();
	this.controller = controller;
	circuit = controller.getPCF();
    }

    public RulerScen1() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void notifyInit() {
	/* Recuperation de la liste des feux et positions */
	ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
		.getListLights().clone();

	ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
		.getListPositions().clone();

	int i, after, captNum, tour = 0;

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
    }

    @Override
    public void notifyUp(int sensorId) {

    }

    @Override
    public void setController(IController cont) {
	this.controller = cont;
	circuit = controller.getPCF();
    }

}
