package trafic.test;

import trafic.network.Commandes;
import trafic.network.SocketCommunicator;
import trafic.network.PCFType;

public class SocketCommunicatorTest {
	public static void main(String[] args) {

		SocketCommunicator n = new SocketCommunicator();
		n.connect("grimau.dynamic-dns.net", 55556);
		n.sendMsg(Commandes.pcf(1, PCFType.request, Commandes.hello(1)));
		String g;
		n.sendMsg(Commandes.pcf(1, PCFType.request, Commandes.start()));
		n.sendMsg(Commandes.pcf(1, PCFType.request, Commandes.bye()));
		while (true) {
			g = n.getNextMsg();
			if (g != null)
				System.out.println(g);
		}

	}
}
