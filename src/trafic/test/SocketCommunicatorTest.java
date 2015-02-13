package trafic.test;

import trafic.cparser.parser.Parser;
import trafic.network.Commandes;
import trafic.network.SocketCommunicator;
import trafic.network.PCFType;

public class SocketCommunicatorTest {
	public static void main(String[] args) {

		SocketCommunicator n = new SocketCommunicator(null);
		n.connect("grimau.dynamic-dns.net", 55556);
		n.sendMsg(Commandes.pcf(1, PCFType.request, Commandes.hello(1)));
		String g;
		n.sendMsg(Commandes.pcf(1, PCFType.advise, Commandes.info(true, "")));
		n.sendMsg(Commandes.pcf(1, PCFType.request, Commandes.start()));
		Parser p = new Parser(null);

		System.out.println(Commandes.pcf(3, PCFType.request,
				Commandes.set(Commandes.train(2, true, true))));
		n.sendMsg(Commandes.pcf(3, PCFType.request,
				Commandes.set(Commandes.train(2, true, true))));
		n.sendMsg(Commandes.pcf(4, PCFType.request,
				Commandes.set(Commandes.train(4, true, true))));


	}
}
