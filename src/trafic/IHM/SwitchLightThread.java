package trafic.IHM;

import javax.swing.JPanel;

import trafic.cparser.TrainIhm;

public class SwitchLightThread extends Thread {
    private TrainIhm l;
    private boolean run = true;
    private JPanel panel;

    public SwitchLightThread(TrainIhm l, JPanel panel) {
	super();
	this.l = l;
	this.panel = panel;
    }

    public void run() {
	while (run) {
	    try {
		Thread.sleep(500);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    l.switchColor();
	}
    }

    public void stopThisThread() {
	run = false;
    }
}
