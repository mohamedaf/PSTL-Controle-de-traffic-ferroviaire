package trafic.network;

import java.util.HashMap;

import trafic.interfaces.ICommunicator;
import trafic.interfaces.IParser;

/**
 * @author KOBROSLI - AFFES
 * 
 *         Classe gerant le thread de comminication client/serveur, elle reçoit
 *         les directives du parser et demande au thread de les communiquer au
 *         serveur.
 */
public class SocketCommunicator implements ICommunicator {

    private ClientThread ct;
    private IParser parser;
    private HashMap<Integer, String> sentMsg = new HashMap<Integer, String>();
    private static int reqid;

    /**
     * Constructeur
     * 
     * @param parser
     *            : Parser
     */
    public SocketCommunicator(IParser parser) {
	this.parser = parser;
	reqid = 0;
    }

    /**
     * Constructeur
     * 
     * @param parser
     *            : Parser
     * @param host
     *            : serverhost
     * @param port
     *            : port
     */
    public SocketCommunicator(IParser parser, String host, int port) {
	reqid = 0;
	this.parser = parser;
	this.ct = new ClientThread(host, port, parser);
    }

    /**
     * Constructeur
     * 
     * @param host
     *            : serverhost
     * @param port
     *            : port
     */
    public SocketCommunicator(String host, int port) {
	reqid = 0;
	this.ct = new ClientThread(host, port, parser);
    }

    /**
     * Initialisation/Modification du parser
     * 
     * @param parser
     *            : nouveau parser
     */
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
    public boolean connect() {
	ct.start();
	return ct.isAlive() && ct.isConnected();
    }

}
