package trafic.IHM;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import trafic.interfaces.StartableStoppable;

public class IHM {
	private final StartableStoppable controller;
	private int numScenario;

	public IHM(StartableStoppable controller) {
		this.controller = controller;
		init();
	}

	private void init() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(600, 512);
		Box vertBox = Box.createVerticalBox();
		Box horBox = Box.createHorizontalBox();

		ImageIcon circuit = new ImageIcon("data/Circuit0.png");
		JLabel circuitLabel = new JLabel(circuit);
		
		ImageIcon train = new ImageIcon("data/Loco.png");
		JLabel trainLabel = new JLabel(train);
		trainLabel.setSize(50, 50);
		
		//horBox.add(trainLabel);
		horBox.add(circuitLabel);

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

		vertBox.add(button1);
		vertBox.add(button2);
		
		
		

		horBox.add(new JSeparator(JSeparator.VERTICAL));
		horBox.add(vertBox);

		jFrame.add(horBox);

		jFrame.setVisible(true);
	}

}
