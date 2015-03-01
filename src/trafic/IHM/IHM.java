package trafic.IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import trafic.interfaces.StartableStoppable;

public class IHM {
    private final StartableStoppable controller;

    public IHM(StartableStoppable controller) {
	this.controller = controller;
	init();
    }

    private void init() {
	JFrame jFrame = new JFrame();
	jFrame.setSize(200, 200);
	Box box = Box.createVerticalBox();

	JButton button1 = new JButton("Start");
	JButton button2 = new JButton("Stop");

	button1.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		controller.start();

	    }
	});

	button2.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		controller.stop();

	    }
	});

	box.add(button1);
	box.add(button2);
	jFrame.add(box);

	jFrame.setVisible(true);
    }

}
