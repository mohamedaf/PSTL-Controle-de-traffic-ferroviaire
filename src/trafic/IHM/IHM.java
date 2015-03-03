package trafic.IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSeparator;

import trafic.interfaces.StartableStoppable;

public class IHM {
	private final StartableStoppable controller;
	private int numScenario;
	private CircuitPanel circuitPanel;

	public IHM(StartableStoppable controller) {
		this.controller = controller;
		circuitPanel = new CircuitPanel("data/Circuit0.png", "data/Loco.png");
		init();
	}

	private void init() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(600, 600);
		Box vertBox = Box.createVerticalBox();
		Box horBox = Box.createHorizontalBox();

		horBox.add(circuitPanel);

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

		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
