package trafic.test;

import trafic.IHM.IHM;
import trafic.control.Controller;
import trafic.interfaces.IRuler;
import trafic.ruler.RulerScen7;

/**
 * @author KOBROSLI - AFFES
 *
 *         Classe de test du scenario 7
 */
public class MainTest {

    public static void main(String[] args) {
	IRuler scenario7 = new RulerScen7();
	Controller c = new Controller(scenario7);
	IHM ihm = new IHM(c);
	c.setIhm(ihm);
    }
}