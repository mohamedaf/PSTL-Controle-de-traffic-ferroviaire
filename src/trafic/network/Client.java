package trafic.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import trafic.network.Enum.PCFType;

public class Client {
    private Socket socket;
    private String hostAdress;
    private int port;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    public Client(String host, int port) {
	super();
	this.hostAdress = host;
	this.port = port;
    }

    public void connect() {
	try {
	    int serverPort = port;
	    InetAddress host = InetAddress.getByName(hostAdress);
	    System.out.println("Connecting to server on port " + serverPort);

	    socket = new Socket(host, serverPort);
	    System.out.println("Just connected to "
		    + socket.getRemoteSocketAddress());
	} catch (UnknownHostException ex) {
	    ex.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void close() {
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

    public void send(String txt) {
	try {
	    if (toServer == null)
		toServer = new PrintWriter(socket.getOutputStream(), true);
	    toServer.println(txt);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public String readAnswer() {
	String line = null;
	try {
	    if (fromServer == null)
		fromServer = new BufferedReader(new InputStreamReader(
			socket.getInputStream()));
	    line = fromServer.readLine();

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return line;
    }

    public static void main(String[] args) {
	Client client = new Client("grimau.dynamic-dns.net", 55556);
	client.connect();

	String s = Commandes.pcf(1, PCFType.request, Commandes.hello(1));
	System.out.println(s);
	client.send(s);
	String answer = client.readAnswer();
	System.out.println("Answer : " + answer);

	client.send(Commandes.pcf(1, PCFType.request, Commandes.start()));
	answer = client.readAnswer();
	System.out.println("Answer : " + answer);

	client.close();
    }
}