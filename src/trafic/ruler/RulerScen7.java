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

/**
 * @author KOBROSLI - AFFES
 * 
 *         Regles du scenario 7
 */
public class RulerScen7 implements IRuler {
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
	public RulerScen7(IController controller) {
		this.controller = controller;
		this.circuit = controller.getPCF();
	}

	public RulerScen7() {
		this.reUpThread = new ReUpThread(5000, this);
	}

	@SuppressWarnings("unchecked")
	@Override
	/* Ca fonctionne, Ne pas toucher, merci :-) */
	public void notifyInit() {
		reUpThread.start();

		/* Recuperation de la liste des feux */
		ArrayList<Light> listLights = (ArrayList<Light>) circuit.getLights()
				.getListLights().clone();

		/* Recuperation de la liste des positions */
		ArrayList<Position> listPos = (ArrayList<Position>) circuit.getInit()
				.getListPositions().clone();

		/* Liste contenant les feux a mettre au vert */
		ArrayList<Light> aMettreAuVert = new ArrayList<Light>();

		/* On utilise HashSet pour qu'il n'y ait pas d'elements en double */
		HashSet<Light> lightsAEnlever = new HashSet<Light>();

		for (Position p : listPos) {
			/* On ajoute a cette liste tous les feux precedant un train */
			lightsAEnlever.addAll(feuxPrecedents(p.getAfter()));
		}

		for (Light l : listLights) {
			boolean vert = true;
			for (Light l2 : lightsAEnlever) {
				if (l.getId() == l2.getId()) {
					vert = false;
					break;
				}
			}
			if (vert) {
				aMettreAuVert.add(l);
			}
		}

		System.out.println("Il faut enlever : " + lightsAEnlever.size());

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

			/* On demarre tous les trains */
			controller.setTrain(p.getTrain().getId(), TrainAction.start, p
					.getTrain().getDirection(), true);
		}
	}

	@Override
	public void notifyUp(int sensorId) {
		Position pos = null;
		Train train = null;
		SensorEdges currentSensorEdges = null;
		Light myLight = null;
		ArrayList<Light> myLightsBefore = feuxPrecedents(circuit
				.getTopography().getSensorEdgesById(sensorId).getCapteur());
		ArrayList<Position> trainsDerriere = new ArrayList<Position>();

		/* Si le train attend a la station, on ne fait rien */
		if (reUpThread.isWaiting(sensorId)) {
			return;
		}

		if (circuit == null) {
			System.err.println("circuit null !!!!!!");
			return;
		}

		/* On recupere le train et le feu se trouvant devant */
		for (Position p : circuit.getInit().getListPositions()) {
			if (p.getAfter().getId() == sensorId) {
				pos = p;
				train = pos.getTrain();
				myLight = circuit.getLights().getLightById(sensorId);
			}
			/*
			 * Si un train est situe avant le capteur precedant le capteur
			 * active
			 */
			for (Light s : myLightsBefore) {
				if (p.getAfter().getId() == s.getId()) {
					trainsDerriere.add(p);
				}
			}
		}

		currentSensorEdges = circuit.getTopography().getSensorEdgesById(
				pos.getBefore().getId());
		/*
		 * Si le train est en etat de marche et est en fin de station, il
		 * s'arrete.
		 */
		if (currentSensorEdges.getCapteur().getType() == SensorType.station) {
			if (train.getAction() == TrainAction.start) {
				System.out.println("Le train " + train.getId()
						+ " arrive en bout de station "
						+ currentSensorEdges.getCapteur().getId());
				controller.setTrain(train.getId(), TrainAction.stop,
						train.getDirection(), false);
				reUpThread.addReUp(sensorId);
				return;
			}
			if (train.getAction() == TrainAction.stop) {
				System.out.println("Le train " + train.getId()
						+ " veut redemarrer de la station "
						+ currentSensorEdges.getCapteur().getId());
			}
		}
		/* Si il y un feu et qu'il est rouge, on arrete le train */
		if (myLight != null && myLight.getColor() == Color.red) {
			System.out.println("Le feu " + myLight.getId()
					+ " est rouge. Le train " + train.getId() + " s'arrete.");
			controller.setTrain(train.getId(), TrainAction.stop,
					train.getDirection(), false);
		} else {
			/* Si le feu est vert ou qu'il n'y a pas de feu */
			int aft = pos.getAfter().getId();

			/* Si le train arrive a un aiguillage */
			if (circuit.getTopography().isPartOfSwitchEdge(aft)) {
				SwitchEdges aiguillage = circuit.getTopography()
						.getSwitchEdgesByBranch(aft);
				if (aiguillage != null)
					dirigerAiguillage(aiguillage, pos);
			}

			System.out.println("Le train " + train.getId() + " peut avancer");
			/* Le train continue d'avancer */
			controller.setTrain(train.getId(), TrainAction.start,
					train.getDirection(), false);

			/* On met le feu qu'on vient de depasser au rouge */
			if (myLight != null) {
				/*
				 * On met le ou les feux precedents (si le train vient de passer
				 * un aiguillage 2-1) au vert
				 */
				for (Light tmpLight : myLightsBefore) {
					controller.setLight(tmpLight.getId(), Color.green);
				}
				controller.setLight(myLight.getId(), Color.red);
			}
			/* Si un train ou plusieurs trains sont a l'arret derriere */
			for (Position p : trainsDerriere) {
				if (p.getTrain().getAction() == TrainAction.stop)
					notifyUp(p.getAfter().getId());
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
	 * Retourne la liste des feux precedent le capteur actuel, Il se peut qu'un
	 * capteur precedent n'aye pas de feu, il faut donc chercher recursivement
	 * les feux
	 * 
	 * @param sensor
	 *            : capteur
	 * @return Le ou les feux precedant le sensor
	 */
	private ArrayList<Light> feuxPrecedents(Sensor sensor) {
		ArrayList<Light> ret = new ArrayList<Light>();
		SensorEdges se = null;

		/* On cherche le sensor edges correspondant a ce sensor */
		for (SensorEdges s : circuit.getTopography().getSensorEdgesList()) {
			if (s.getCapteur().getId() == sensor.getId()) {
				se = s;
				break;
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
	 * Diriger l'aiguillage dans le bon sens, a l'initialisation
	 * 
	 * @param aiguillage
	 * @param pos
	 */
	private void dirigerAiguillageInit(SwitchEdges aiguillage, Position pos) {
		Sensor s = null;

		/* On recupere le capteur vers lequel l'aguillage doit etre fait */
		if (aiguillage.getType() == SwitchType._1_2) {
			s = pos.getAfter();
		} else if (aiguillage.getType() == SwitchType._2_1) {
			s = pos.getBefore();
		}

		/* Modifier l'aiguillage en consequence */
		if (aiguillage.isBranch0(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b0);
		} else if (aiguillage.isBranch1(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b1);
		}
	}

	/**
	 * Diriger l'aiguillage dans le bon sens, le train est en mouvement juste
	 * avant l'aiguillage
	 * 
	 * @param aiguillage
	 * @param pos
	 */
	private void dirigerAiguillage(SwitchEdges aiguillage, Position pos) {
		Sensor s = pos.getAfter();
		boolean twoOne = false;
		if (aiguillage.getType() == SwitchType._2_1) {
			twoOne = true;
		}
		/* Si le train se dirige vers la branche 0 */
		if (aiguillage.isBranch0(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b0);
			/* On met le feu permettant d'entrer sur l'autre branche au rouge */
			if (twoOne)
				controller.setLight(aiguillage.getBranch1(), Color.red);
		} /* Si le train se dirige vers la branche 1 */
		else if (aiguillage.isBranch1(s.getId())) {
			controller.setSwitch(aiguillage.getId(), SwitchPos.b1);
			/* On met le feu permettant d'entrer sur l'autre branche au rouge */
			if (twoOne)
				controller.setLight(aiguillage.getBranch0(), Color.red);
		}
	}

}