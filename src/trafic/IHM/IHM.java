package trafic.IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import trafic.IHM.Panels.AutomaticCircuitPanel;
import trafic.IHM.Panels.CircuitPanel0;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.SensorEdges;
import trafic.interfaces.ICircuitPanel;
import trafic.interfaces.IIhm;
import trafic.interfaces.IStartStop;

public class IHM implements IIhm {
	private final IStartStop controller;
	private int numScenario;
	private ICircuitPanel circuitPanel;
	private JTextArea address;
	private JTextArea port;
	JFrame jFrame;
	private Pcf circuit;

	public IHM(IStartStop controller) {
		this.controller = controller;

		initPrincipalFrame();
	}

	private void initPrincipalFrame() {
		jFrame = new JFrame();
		jFrame.setSize(700, 600);
		jFrame.setTitle("Controle de trafic ferroviaire");
		Box vertBox = Box.createVerticalBox();
		Box horBox = Box.createHorizontalBox();

		Dimension d = new Dimension(120, 40);
		address = new JTextArea("grimau.dynamic-dns.net");
		address.setMinimumSize(d);
		address.setMaximumSize(d);
		port = new JTextArea("" + 55558);
		port.setMinimumSize(d);
		port.setMaximumSize(d);

		JScrollPane jp1 = new JScrollPane(address);
		jp1.setMinimumSize(d);
		jp1.setMaximumSize(d);

		JScrollPane jp2 = new JScrollPane(port);
		jp2.setMinimumSize(d);
		jp2.setMaximumSize(d);

		vertBox.add(jp1);
		vertBox.add(jp2);

		JButton button1 = new JButton("Start");
		JButton button2 = new JButton("Stop");

		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int p = 0;
				try {
					p = Integer.parseInt(port.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				controller.start(address.getText(), p);
			}
		});

		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.stop();

			}
		});

		Dimension d2 = new Dimension(120, 30);
		button1.setMinimumSize(d2);
		button1.setMaximumSize(d2);
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.setMinimumSize(d2);
		button2.setMaximumSize(d2);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);

		vertBox.add(button1);
		vertBox.add(button2);

		horBox.add(vertBox);
		horBox.add(new JSeparator(JSeparator.VERTICAL));

		jFrame.add(horBox);
		jFrame.setVisible(true);

		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void init0(Pcf circuit) {
		this.circuit = circuit;

		ArrayList<SensorEdges> sensorList = circuit.getTopography()
				.getSensorEdgesList();

		ArrayList<Integer> sensorIdTab = new ArrayList<Integer>(
				sensorList.size());

		for (int i = 0; i < sensorList.size(); i++) {
			sensorIdTab.add(i, sensorList.get(i).getCapteur().getId());

		}

		Collections.sort(sensorIdTab);

		Box horBox = (Box) jFrame.getContentPane().getComponent(0);

		jFrame.getContentPane().remove(0);
		JPanel c = circuitPanel.getComponent();
		c.setAlignmentX(Component.LEFT_ALIGNMENT);
		horBox.add(c);
		jFrame.add(horBox);

		jFrame.setVisible(true);
		jFrame.repaint();
	}

	@Override
	public void notifyInit(Pcf circuit) {
		numScenario = circuit.getScenario().getId();
		this.circuit = circuit;
		switch (numScenario) {
		case 0:
			circuitPanel = new CircuitPanel0("data/Circuit0.png",
					"data/Loco.png");
			System.out.println("\nScenario 0 IHM ok\n");
			init0(circuit);
			break;
		default:
			circuitPanel = new AutomaticCircuitPanel(500, 600, circuit,
					"data/Loco.png");
			break;
		}

		Box horBox = (Box) jFrame.getContentPane().getComponent(0);
		jFrame.getContentPane().remove(0);
		JPanel c = circuitPanel.getComponent();
		c.setAlignmentX(Component.LEFT_ALIGNMENT);
		horBox.add(c);
		jFrame.add(horBox);

		jFrame.setVisible(true);
		jFrame.repaint();

	}

	@Override
	public void switchLight(int lightId) {
		circuitPanel.switchLight(lightId);
	}

	@Override
	public void notifyUp(int sensorId) {
		for (Position p : circuit.getInit().getListPositions()) {
			if (p.getAfter().getId() == sensorId) {
				step(p.getTrain().getId());
				break;
			}
		}
		circuitPanel.notifyUp(sensorId);

	}

	@Override
	public void step(int idTrain) {
		circuitPanel.step(idTrain);
		System.out.println("step " + idTrain);
	}

}
