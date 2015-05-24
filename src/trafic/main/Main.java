package trafic.main;

import trafic.IHM.IHM;
import trafic.control.Controller;
import trafic.interfaces.IRuler;
import trafic.ruler.RulerScen7;

public class Main {

	public static void main(String[] args) {
		IRuler scenario7 = new RulerScen7();
		Controller c = new Controller(scenario7);
		IHM ihm = new IHM(c);
		c.setIhm(ihm);
	}

}
