package trafic.ruler.thread;

import java.util.HashMap;
import java.util.Map;

import trafic.interfaces.IRuler;

public class ReUpThread extends Thread {
    private HashMap<Integer, Long> map;
    private long waitingTime;
    private boolean running;
    private IRuler ruler;

    public ReUpThread(long waitingTime, IRuler ruler) {
	super();
	this.map = new HashMap<Integer, Long>();
	this.waitingTime = waitingTime;
	this.ruler = ruler;
    }

    public void run() {
	running = true;

	while (running) {
	    long time = System.currentTimeMillis();
	    for (Map.Entry<Integer, Long> entry : map.entrySet()) {
		if (entry.getValue() + waitingTime > time) {
		    ruler.notifyUp(entry.getKey());
		}
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
