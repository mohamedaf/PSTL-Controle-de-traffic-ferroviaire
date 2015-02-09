package trafic.retransmit;

import java.util.HashMap;

import trafic.interfaces.ICommunicator;

public class RetransmitThread extends Thread {
	private HashMap<Integer, DatedMessage> listeMsg;
	private ICommunicator communicator;
	private int timeBeforeRetransmit;
	private boolean running;

	public RetransmitThread(int timeBeforeRetransmit, ICommunicator communicator) {
		listeMsg = new HashMap<Integer, DatedMessage>();
		this.communicator = communicator;
		this.timeBeforeRetransmit = timeBeforeRetransmit;
	}
	
	public void run() {
		this.running = true;
		long currentTime;
		while (running) {
			currentTime = System.currentTimeMillis();
			for(DatedMessage dm : listeMsg.values()){
				if(currentTime - dm.getTime() >= timeBeforeRetransmit){
					communicator.sendMsg(dm.getMessage());
				}
			}
			
		}

	}

	public void addMsg(int reqid, DatedMessage dm) {
		listeMsg.put(reqid, dm);
	}

	public void removeMsg(int reqid) {
		listeMsg.remove(reqid);
	}

	public void stopThread() {
		this.running = false;
	}

}
