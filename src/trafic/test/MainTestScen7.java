package trafic.test;

import trafic.IHM.IHM;
import trafic.control.Controller;
import trafic.interfaces.ICommunicator;
import trafic.interfaces.IRuler;
import trafic.network.SocketCommunicator;
import trafic.ruler.RulerScen7;

public class MainTestScen7 {

	public static void main(String[] args) {
		IRuler scenario7 = new RulerScen7();
		//ICommunicator com = new SocketCommunicator("grimau.dynamic-dns.net", 55558); 
		Controller c = new Controller(scenario7);
		IHM ihm = new IHM(c);
		c.setIhm(ihm);
	}
}