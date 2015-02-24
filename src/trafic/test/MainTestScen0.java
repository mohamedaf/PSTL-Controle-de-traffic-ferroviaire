package trafic.test;

import trafic.IHM.IHM;
import trafic.control.Controller;
import trafic.interfaces.IRuler;
import trafic.ruler.RulerScen0;

public class MainTestScen0 {

    public static void main(String[] args) {
	IRuler scenario0 = new RulerScen0();
	Controller c = new Controller(scenario0);
	IHM ihm = new IHM(c);
    }
}
