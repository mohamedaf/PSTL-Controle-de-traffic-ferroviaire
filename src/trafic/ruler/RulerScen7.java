package trafic.ruler;

import java.util.ArrayList;
import java.util.HashSet;

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
import trafic.enums.SwitchType;
import trafic.enums.TrainAction;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;
import trafic.ruler.thread.ReUpThread;

public class RulerScen7 implements IRuler {
	private final static int numScen = 1;

	private IController controller;
	private Pcf circuit;

	private ReUpThread reUpThread;

	public RulerScen7(IController controller) {
		super();
		this.controller = controller;
		this.circuit = controller.getPCF();
		this.reUpThread = new ReUpThread(5000, this);
	}
	
	public RulerScen7() {
	}

	@SuppressWarnings("unchecked")
	@Override
	
	/* Ca fonctionne, Ne pas toucher, merci :-) */
	public void notifyInit() {
		ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
				.getListLights().clone();

		ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
				.getListPositions().clone();
		
		ArrayList<Light> aMettreAuVert = new ArrayList<Light>();

		/* On utilise HashSet pour qu'il n'y ait pas d'éléments en double */
		HashSet<Light> lightsAEnlever = new HashSet<Light>();

		for (Position p : listPos) {
			/* On ajoute a cette liste tous les feux precedant un train */
			lightsAEnlever.addAll(feuxPrecedents(p.getAfter()));
		}
		
		
		for(Light l : listLights){
			boolean vert = true;
			for(Light l2 : lightsAEnlever){
				if(l.getId() == l2.getId()){
					vert = false;
					break;
				}
			}
			if(vert){
				aMettreAuVert.add(l);
			}
		}
		
		System.out.println("Il faut enlever : "+lightsAEnlever.size());

		/* Tous les autres feux sont mis au vert */
		for (Light l : aMettreAuVert) {
			controller.setLight(l.getId(), Color.green);
		}

		for (Position p : listPos) {
			/*
			 * Si le train est situe sur un aiguillage, on dirige l'aiguillage
			 * vers le train
			 */
			if (circuit.getTopography().isPartOfSwitchEdge(
					p.getBefore().getId())) {
				/* Aiguillage 2-1 */
				SwitchEdges sw = circuit.getTopography()
						.getSwitchEdgesByBranch(p.getBefore().getId());

				/* Aiguillage 1-2 */
				if (sw == null) {
					sw = circuit.getTopography().getSwitchEdgesByTrunk(
							p.getBefore().getId());
				}

				dirigerAiguillageInit(sw, p);
			}
			/* On démarre tous les trains */
			controller.setTrain(p.getTrain().getId(), TrainAction.start, p
					.getTrain().getDirection(), true);
		}
	}

	@Override
	
	/*Pas fini */
	public void notifyUp(int sensorId) {
		Position pos = null;
		Train train = null;
		SensorEdges currentSensorEdges = null;
		Light myLight = null;
		ArrayList<Light> myLightsBefore = new ArrayList<Light>();

		for (Position p : circuit.getInit().getListPositions()) {
			if (p.getAfter().getId() == sensorId) {
				pos = p;
				train = pos.getTrain();
				myLight = circuit.getLights().getLightById(sensorId);
				break;
			}
		}

		currentSensorEdges = circuit.getTopography().getSensorEdgesById(
				pos.getBefore().getId());

		/*
		 * Si le train est en etat de marche et est en fin de station, il
		 * s'arrete.
		 */
		if (currentSensorEdges.getCapteur().getType() == SensorType.station
				&& train.getAction() == TrainAction.start) {
			controller.setTrain(train.getId(), TrainAction.stop,
					train.getDirection(), false);
			reUpThread.addReUp(sensorId);
			return;
		}

		/* Si il y un feu et qu'il est rouge, on arrete le train */
		if (myLight != null && myLight.getColor() == Color.red) {
			controller.setTrain(train.getId(), TrainAction.stop,
					train.getDirection(), false);
		} else {
			int aft = pos.getAfter().getId();
			/* Si le train arrive a un aiguillage */
			if (circuit.getTopography().isPartOfSwitchEdge(aft)) {
				SwitchEdges aiguillage = circuit.getTopography()
						.getSwitchEdgesByBranch(aft);
				if (aiguillage != null)
					dirigerAiguillage(aiguillage, pos);
			}
		}

	}

	@Override
	public void setController(IController cont) {
		this.controller = cont;
		this.circuit = controller.getPCF();

	}

	@Override
	public int getNumScen() {
		return numScen;
	}

	/**
	 * @param sensor
	 * @return Le ou les feux precedant le sensor
	 */
	private ArrayList<Light> feuxPrecedents(Sensor sensor) {
		ArrayList<Light> ret = new ArrayList<Light>();
		SensorEdges se = null;

		/* On cherche le sensor edges correspondant a ce sensor */
		for (SensorEdges s : circuit.getTopography().getSensorEdgesList()) {
			if (s.getCapteur().getId() == sensor.getId()) {
				se = s;
			}
		}

		Light l;

		/* On cherche tous les capteurs precedents (in) */
		for (Sensor s : se.getCapteurInList()) {
			l = circuit.getLights().getLightById(s.getId());

			/*
			 * Si le capteur n'a pas de feu correspondant, appel recursif pour
			 * chercher les feux precedents
			 */
			if (l == null) {
				ret.addAll(feuxPrecedents(s));
			}
			/* Sinon on ajoute le feu a la liste */
			else {
				ret.add(l);
			}
		}

		return ret;
	}

	/**
	 * @param aiguillage
	 * @param pos
	 */
	private void dirigerAiguillageInit(SwitchEdges aiguillage, Position pos) {
		Sensor s = null;
		if (aiguillage.getType() == SwitchType._1_2) {
			s = pos.getAfter();
		} else if (aiguillage.getType() == SwitchType._2_1) {
			s = pos.getBefore();
		}
		if (aiguillage.isBranch0(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b0);
		} else if (aiguillage.isBranch1(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b1);
		}
	}

	private void dirigerAiguillage(SwitchEdges aiguillage, Position pos) {
		Sensor s = pos.getAfter();
		if (aiguillage.isBranch0(s.getId()))
			controller.setSwitch(aiguillage.getId(), SwitchPos.b0);
		else if (aiguillage.isBranch1(s.getId()))
			controller.setSwitch(aiguillage.getId(), SwitchPos.b1);
	}

}
