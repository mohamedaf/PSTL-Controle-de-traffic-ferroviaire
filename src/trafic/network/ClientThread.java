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

/**
 * @author KOBROSLI - AFFES
 * 
 *         Thread gerant les interactions client/serveur
 */
public class ClientThread extends Thread {
    private Socket socket;
    private String hostAdress;
    private int port;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private volatile boolean running = true;
    private LinkedBlockingQueue<String> sendArray;
    private IParser parser;

    /**
     * Constructeur
     * 
     * @param host
     *            : serverhost
     * @param port
     *            : port
     * @param parser
     *            : Parser
     */
    public ClientThread(String host, int port, IParser parser) {
	super();
	this.hostAdress = host;
	this.port = port;
	this.sendArray = new LinkedBlockingQueue<String>();
	this.parser = parser;
    }

    /**
     * Verifie si une connexion est en cours
     * 
     * @return true si socket non null false sinon
     */
    public boolean isConnected() {
	if (socket != null)
	    return !socket.isClosed();
	return false;
    }

    @Override
    public synchronized void run() {
	// Connexion au moniteur (serveur)
	connect();
	String msg;
	System.out.println("Running thread ...");
	while (running) {
	    while (!sendArray.isEmpty()) {
		try {
		    msg = sendArray.take();
		    // envoi du message au moniteur (serveur)
		    send(msg);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }

	    try {
		// recuperation de la reponse du moniteur (serveur)
		readAnswer();
	    } catch (IOException e) {
		// e.printStackTrace();
	    }

	}
	// fermeture de la connexion
	close();
	System.out.println("Connection close");
    }

    /**
     * Connexion au moniteur (serveur)
     */
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

    /**
     * Deconnexion avec le moniteur (serveur)
     */
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

    /**
     * Envoie du message au moniteur (serveur)
     * 
     * @param txt
     *            : message a envoyer
     */
    private void send(String txt) {
	try {
	    if (toServer == null)
		toServer = new PrintWriter(socket.getOutputStream(), true);
	    toServer.println(txt);
	    toServer.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Ajout du message a la file d'envoi
     * 
     * @param txt
     *            : message a ajouter a la file
     */
    public void sendMsg(String txt) {
	sendArray.offer(txt);
    }

    /**
     * Retourne le message provenant du moniteur (serveur)
     * 
     * @return message provenant du moniteur (serveur)
     * @throws IOException
     */
    private String readAnswer() throws IOException {
	String line = null;
	if (fromServer == null)
	    fromServer = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	line = fromServer.readLine();
	if (line != null) {
	    System.out.println(line);
	    parser.parse(line);
	}
	return line;
    }

    /**
     * Methode permettant d'arreter le Thread
     */
    public void stopThread() {
	System.out.println("Demande stop recue ...");
	running = false;
    }

}
