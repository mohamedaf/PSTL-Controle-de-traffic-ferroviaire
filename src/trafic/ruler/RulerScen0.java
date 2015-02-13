package trafic.ruler;

import trafic.interfaces.IController;
import trafic.interfaces.IUpNotifier;

public class RulerScen0 implements IUpNotifier {
	private IController controller;

	public RulerScen0(IController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void notifyUp(int sensorId) {
		// TODO Auto-generated method stub
	}

}
