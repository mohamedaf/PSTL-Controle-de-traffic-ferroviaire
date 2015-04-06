package trafic.ruler.thread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import trafic.interfaces.IRuler;

public class ReUpThread extends Thread {
	private HashMap<Integer, Long> map;
	private long waitingTime;
	private boolean running;
	private IRuler ruler;

	public ReUpThread(long waitingTimeMillis, IRuler ruler) {
		super();
		this.map = new HashMap<Integer, Long>();
		this.waitingTime = waitingTimeMillis;
		this.ruler = ruler;
	}

	public void run() {
		running = true;
		HashSet<Long> aEnlever = new HashSet<Long>();

		while (running) {
			long time;
			aEnlever.clear();
			for (Map.Entry<Integer, Long> entry : map.entrySet()) {
				time = System.currentTimeMillis();
				/*
				 * Si le temps d'attente est dépassé, on essaye de relancer la
				 * train et on supprime le capteur de la liste (map) d'attente
				 */
				if (entry.getValue() + waitingTime > time) {
					ruler.notifyUp(entry.getKey());
					aEnlever.add(entry.getValue());
				}
			}
			for (Long l : aEnlever) {
				map.remove(l);
			}

		}
	}

	public void addReUp(int sensorId) {
		map.put(sensorId, System.currentTimeMillis());
	}

	public void stopThread() {
		running = false;
	}
}
