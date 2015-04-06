package trafic.test;

import trafic.IHM.IHM;
import trafic.control.Controller;
import trafic.interfaces.IRuler;
import trafic.ruler.RulerScen0;
import trafic.ruler.RulerScen1;

public class MainTestScen1 {

	public static void main(String[] args) {
		IRuler scenario1 = new RulerScen1();
		Controller c = new Controller(scenario1);
		IHM ihm = new IHM(c);
		c.setIhm(ihm);
	}
}