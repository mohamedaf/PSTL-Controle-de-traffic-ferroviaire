package trafic.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

import trafic.interfaces.IParser;

public class ClientThread extends Thread {
    private Socket socket;
    private String hostAdress;
    private int port;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private volatile boolean running = true;
    private LinkedBlockingQueue<String> sendArray;
    private LinkedBlockingQueue<String> receivedArray;
    private IParser parser;

    public ClientThread(String host, int port, IParser parser) {
	super();
	this.hostAdress = host;
	this.port = port;
	this.sendArray = new LinkedBlockingQueue<String>();
	this.receivedArray = new LinkedBlockingQueue<String>();
	this.parser = parser;
    }

    public boolean isConnected() {
	if (socket != null)
	    return !socket.isClosed();
	return false;
    }

    @Override
    public synchronized void run() {
	connect();
	String msg;
	System.out.println("Running thread ...");
	while (running) {
	    while (!sendArray.isEmpty()) {
		try {
		    msg = sendArray.take();
		    send(msg);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }

	    try {
		readAnswer();
	    } catch (IOException e) {
		// e.printStackTrace();
	    }
	    

	}
	close();
	System.out.println("Thread close");
    }

    private void connect() {
	try {
	    InetAddress host = InetAddress.getByName(hostAdress);
	    System.out.println("Connecting to server on port " + port);

	    socket = new Socket(host, port);
	    socket.setSoTimeout(100);
	    System.out.println("Just connected to "
		    + socket.getRemoteSocketAddress());
	} catch (UnknownHostException ex) {
	    ex.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void close() {
	try {
	    if (toServer != null)
		toServer.close();
	    if (fromServer != null)
		fromServer.close();
	    socket.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	System.out.println("Socket close");
    }

    private void send(String txt) {
	try {
	    if (toServer == null)
		toServer = new PrintWriter(socket.getOutputStream(), true);
	    toServer.println(txt);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void sendMsg(String txt) {
	sendArray.offer(txt);
    }

    private String readAnswer() throws IOException {
	String line = null;
	if (fromServer == null)
	    fromServer = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	line = fromServer.readLine();
	if (line != null) {
	    // receivedArray.offer(line);
	    System.out.println(line);
	    parser.parse(line);
	}
	return line;
    }

    public void stopThread() {
	System.out.println("Demande stop recue ...");
	running = false;
    }

    public String getNextMsg() {
	try {
	    if (!receivedArray.isEmpty())
		return receivedArray.take();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return null;
	}
	return null;
    }

}
