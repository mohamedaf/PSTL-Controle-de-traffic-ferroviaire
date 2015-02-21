package trafic.network;

import java.util.HashMap;

import trafic.interfaces.ICommunicator;
import trafic.interfaces.IParser;

public class SocketCommunicator implements ICommunicator {

    private ClientThread ct;
    private IParser parser;
    private HashMap<Integer, String> sentMsg;
    private static int reqid;

    public SocketCommunicator(IParser parser) {
	this.parser = parser;
	sentMsg = new HashMap<Integer, String>();
	reqid = 0;
    }

    @Override
    public void close() {
	if (ct != null && ct.isAlive()) {
	    ct.stopThread();
	    System.out.println("Socket close");
	}
    }

    @Override
    public void sendMsg(String txt) {
	if (ct != null){
	    ct.sendMsg(txt);
	    sentMsg.put(reqid++, txt);
	}
    }

    @Override
    public boolean connect(String host, int port) {
	ct = new ClientThread(host, port, parser);
	ct.start();
	return ct.isAlive() && ct.isConnected();
    }

    @Override
    public void acquit(int id) {
	// TODO Auto-generated method stub

    }

}
