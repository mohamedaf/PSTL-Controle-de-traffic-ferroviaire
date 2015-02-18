package trafic.test;

import trafic.control.Controller;
import trafic.ruler.RulerScen0;

public class MainTestScen0 {

    public static void main(String[] args) {
	RulerScen0 scenario0 = new RulerScen0();
	Controller c = new Controller(scenario0);
	scenario0.setController(c);
    }
}
