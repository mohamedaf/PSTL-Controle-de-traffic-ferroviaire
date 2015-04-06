package trafic.chemin;

import java.util.ArrayList;
import java.util.Random;

import trafic.elements.Pcf;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;

public class Chemin {
	private int trainId;
	private ArrayList<Integer> enchainement = new ArrayList<Integer>();

	public Chemin(int trainId, ArrayList<Integer> enchainement) {
		super();
		this.trainId = trainId;
		this.enchainement = enchainement;
	}

	public Chemin(int trainId) {
		super();
		this.trainId = trainId;
	}

	public int getNextCanton(int currentCanton) throws SensorNotFoundException {
		int pos = -1;
		for (int i = 0; i < enchainement.size(); i++) {
			if (enchainement.get(i) == currentCanton) {
				pos = i;
				break;
			}
		}
		if (pos == -1)
			throw new SensorNotFoundException(currentCanton);

		return enchainement.get((pos + 1) % enchainement.size());
	}

	public static boolean estCheminRealisable(Chemin c, Pcf circuit) {
		return false;
	}

	public ArrayList<Integer> getEnchainement() {
		return enchainement;
	}

	public void setEnchainement(ArrayList<Integer> enchainement) {
		this.enchainement = enchainement;
	}

	public int getTrainId() {
		return trainId;
	}

	public static ArrayList<Integer> genererEnchainement(Pcf circuit)
			throws SensorNotFoundException {
		/* ArrayList<Integer> res = new ArrayList<Integer>(); */
		ArrayList<SensorEdges> listCantons = circuit.getTopography()
				.getSensorEdgesList();
		Random r = new Random();
		/*
		 * SensorEdges depart = listCantons.get(r.nextInt(listCantons.size()));
		 * res.add(depart.getCapteur().getId()); int size =
		 * depart.getCapteurOutList().size(); int next =
		 * depart.getCapteurOutList().get(r.nextInt(size)).getId(); do {
		 * res.add(next); SensorEdges tmp =
		 * circuit.getTopography().getSensorEdgesById(next); ArrayList<Sensor>
		 * liste = tmp.getCapteurOutList();
		 * 
		 * next = liste.get(r.nextInt(liste.size())).getId();
		 * 
		 * } while (next != depart.getCapteur().getId());
		 */

		return genererEnchainement(circuit,
				listCantons.get(r.nextInt(listCantons.size())).getCapteur()
						.getId());
	}

	public static ArrayList<Integer> genererEnchainement(Pcf circuit,
			int idDepart) throws SensorNotFoundException {
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<SensorEdges> listCantons = circuit.getTopography()
				.getSensorEdgesList();
		Random r = new Random();

		SensorEdges depart = null;

		for (SensorEdges t : listCantons) {
			if (t.getCapteur().getId() == idDepart) {
				depart = t;
				break;
			}
		}
		if (depart == null)
			throw new SensorNotFoundException(idDepart);

		res.add(depart.getCapteur().getId());
		int size = depart.getCapteurOutList().size();
		int next = depart.getCapteurOutList().get(r.nextInt(size)).getId();
		do {
			res.add(next);
			SensorEdges tmp = circuit.getTopography().getSensorEdgesById(next);
			ArrayList<Sensor> liste = tmp.getCapteurOutList();

			next = liste.get(r.nextInt(liste.size())).getId();
		} while (!res.contains(next)/* next != depart.getCapteur().getId() */);

		return res;
	}

	@Override
	public String toString() {
		String s = "Chemin [trainId=" + trainId + ", enchainement= ";
		for (Integer i : enchainement) {
			s += i + " ->";
		}
		return s.substring(0, s.length() - 3);
	}

}
