package trafic.IHM;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSeparator;

import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.SensorEdges;
import trafic.interfaces.ICircuitPanel;
import trafic.interfaces.IIhm;
import trafic.interfaces.StartableStoppable;

public class IHM implements IIhm {
	private final StartableStoppable controller;
	private int numScenario;
	private ICircuitPanel circuitPanel;
	JFrame jFrame;
	private Pcf circuit;

	public IHM(StartableStoppable controller) {
		this.controller = controller;
		circuitPanel = new CircuitPanel0("data/Circuit0.png", "data/Loco.png");
		initPrincipalFrame();
	}

	private void initPrincipalFrame() {
		jFrame = new JFrame();
		jFrame.setSize(600, 600);
		Box vertBox = Box.createVerticalBox();
		Box horBox = Box.createHorizontalBox();
		
		final JCheckBox chemin = new JCheckBox("Chemin aléatoire", false);
		chemin.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				System.out.println("Change : "+chemin.isSelected());
				
			}
		});
		
		vertBox.add(chemin);

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

		for (Position p : circuit.getInit().getListPositions()) {
			int pos = -1;
			if (sensorIdTab.size() >= 3) {
				if (p.getAfter().getId() == sensorIdTab.get(0)) {
					pos = CircuitPanel0.POS_3_1;
				} else if (p.getAfter().getId() == sensorIdTab.get(1)) {
					pos = CircuitPanel0.POS_1_2;
				} else if (p.getAfter().getId() == sensorIdTab.get(2)) {
					pos = CircuitPanel0.POS_2_3;
				} else {
					System.err
							.println("Erreur : Position du train inconnue dans le circuit de l'IHM");
				}
				circuitPanel.addTrain(p.getTrain().getId(), pos);
			} else {
				System.err.println("Erreur de taille");
			}

		}

		Box horBox = (Box) jFrame.getContentPane().getComponent(0);
		jFrame.getContentPane().remove(0);
		horBox.add(circuitPanel.getComponent());

		jFrame.add(horBox);

		jFrame.setVisible(true);
		jFrame.repaint();
	}

	@Override
	public void notifyInit(Pcf circuit) {
		numScenario = circuit.getScenario().getId();
		switch (numScenario) {
		case 0:
			circuitPanel = new CircuitPanel0("data/Circuit0.png",
					"data/Loco.png");
			System.out.println("\nScénario 0 IHM ok\n");
			init0(circuit);
			break;
		default:
			System.err.println("Erreur : scenario inconnu.");
			break;
		}

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
		System.out.println("step "+idTrain);
	}

}
