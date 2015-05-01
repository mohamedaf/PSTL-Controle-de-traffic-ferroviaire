package trafic.test;

import trafic.cparser.toxml.Commandes;
import trafic.enums.PCFType;
import trafic.interfaces.ICommunicator;
import trafic.network.SocketCommunicator;

public class OtherTest {
	public static void main(String[] args) {
		ICommunicator com = new SocketCommunicator("grimau.dynamic-dns.net",
				55558);
		com.connect();
		String r = Commandes.pcf(1, PCFType.request,
				Commandes.set(Commandes.switchXml(3, 1)));
		System.out.println(r);
		com.sendMsg(r);

	}
}
