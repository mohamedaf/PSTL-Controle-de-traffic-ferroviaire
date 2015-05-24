package trafic.ruler.thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import trafic.interfaces.IRuler;

/**
 * @author KOBROSLI - AFFES
 * 
 *         Thread permettant de similer l'arret d'un train dans une station
 */
public class ReUpThread extends Thread {
	private HashMap<Integer, Long> map;
	private long waitingTime;
	private boolean running;
	private IRuler ruler;

	/**
	 * Constructeur
	 * 
	 * @param waitingTimeMillis
	 *            : temps d'arret dans une station
	 * @param ruler
	 *            : regle du scenario applique
	 */
	public ReUpThread(long waitingTimeMillis, IRuler ruler) {
		super();
		this.map = new HashMap<Integer, Long>();
		this.waitingTime = waitingTimeMillis;
		this.ruler = ruler;
	}

	@Override
	public void run() {
		running = true;
		HashSet<Integer> aEnlever = new HashSet<Integer>();

		while (running) {
			long time;
			aEnlever.clear();
			synchronized (map) {
				for (Map.Entry<Integer, Long> entry : map.entrySet()) {
					time = System.currentTimeMillis();
					/*
					 * Si le temps d'attente est d�pass�, on essaye de
					 * relancer la train et on supprime le capteur de la liste
					 * (map) d'attente
					 */
					if (entry.getValue() + waitingTime < time) {
						System.out
								.println("Un train est pret a quitter la station");
						aEnlever.add(entry.getKey());
					}
				}
			}
			for (Integer l : aEnlever) {
				map.remove(l);
				ruler.notifyUp(l);
			}

		}
	}

	/**
	 * ajout d'une station a la map
	 * 
	 * @param sensorId
	 */
	public void addReUp(int sensorId) {
		synchronized (map) {
			map.put(sensorId, System.currentTimeMillis());
		}
	}

	/**
	 * Verifier si la station est dans la map
	 * 
	 * @param sensorId
	 *            : identifiant de la station
	 * @return true si la station est contenue dans la map et false sinon
	 */
	public boolean isWaiting(int sensorId) {
		synchronized (map) {
			return map.containsKey(sensorId);
		}
	}

	/**
	 * Permet l'arret du thread
	 */
	public void stopThread() {
		running = false;
	}
}
