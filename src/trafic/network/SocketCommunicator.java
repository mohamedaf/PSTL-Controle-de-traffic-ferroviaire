package trafic.network;

import java.util.HashMap;

import trafic.interfaces.ICommunicator;
import trafic.interfaces.IParser;

public class SocketCommunicator implements ICommunicator {

	private ClientThread ct;
	private IParser parser;
	private HashMap<Integer, String> sentMsg = new HashMap<Integer, String>();
	private static int reqid;

	public SocketCommunicator(IParser parser) {
		this.parser = parser;
		reqid = 0;
	}

	public SocketCommunicator(IParser parser, String host, int port) {
		reqid = 0;
		this.parser = parser;
		this.ct = new ClientThread(host, port, parser);
	}

	public SocketCommunicator(String host, int port) {
		reqid = 0;
		this.ct = new ClientThread(host, port, parser);
	}

	public void setParser(IParser parser) {
		this.parser = parser;
	}

	@Override
	public void close() {
		if (ct != null && ct.isAlive()) {
			ct.stopThread();
		}
	}

	@Override
	public void sendMsg(String txt) {
		if (ct != null) {
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

	@Override
	public boolean connect() {
		ct.start();
		return ct.isAlive() && ct.isConnected();
	}

}
