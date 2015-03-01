package trafic.ruler;

import trafic.elements.Pcf;
import trafic.interfaces.IController;
import trafic.interfaces.IRuler;

public class RulerScen1 implements IRuler {
    private IController controller;
    private Pcf circuit;

    public RulerScen1(IController controller) {
	super();
	this.controller = controller;
	circuit = controller.getPCF();
    }

    public RulerScen1() {

    }

    @Override
    public void notifyInit() {

    }

    @Override
    public void notifyUp(int sensorId) {

    }

    @Override
    public void setController(IController cont) {
	this.controller = cont;
	circuit = controller.getPCF();
    }

}
