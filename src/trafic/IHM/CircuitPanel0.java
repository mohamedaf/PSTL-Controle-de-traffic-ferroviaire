package trafic.IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import trafic.interfaces.ICircuitPanel;

public class CircuitPanel0 extends JPanel implements ICircuitPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4268679183602296707L;
	private Image circuit;
	private Image train;
	private HashMap<Integer, Integer> mapPos;
	private HashMap<Integer, Lumiere> mapLight;
	private HashMap<Integer, Lumiere> mapSensor;

	private int[][] listPosAngle = { { 256, 45, 55, 200, 342, 447 },
			{ 48, 230, 326, 453, 391, 230 }, { 10, 270, 238, 180, 143, 90 } };

	public final static int POS_0_1 = 1, POS_1_2 = 3, POS_2_0 = 5;

	public CircuitPanel0(String circuit, String train) {
		mapPos = new HashMap<Integer, Integer>();
		mapLight = new HashMap<Integer, Lumiere>(3);
		mapSensor = new HashMap<Integer, Lumiere>(3);

		try {
			this.circuit = ImageIO.read(new File(circuit));
			this.train = ImageIO.read(new File(train));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.train = ImageTool.resize(this.train,
				this.train.getWidth(this) / 8, this.train.getHeight(this) / 8);

		setMinimumSize(new Dimension(512, 512));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 512));

		Lumiere light0, light1, light2;
		Lumiere sensor0, sensor1, sensor2;

		light0 = new Lumiere(Color.GREEN, Color.RED, 245, 74, 23, 23);
		sensor0 = new Lumiere(Color.YELLOW, Color.BLACK, 312, 85, 23, 23);

		light1 = new Lumiere(Color.GREEN, Color.RED, 73, 404, 23, 23);
		sensor1 = new Lumiere(Color.YELLOW, Color.BLACK, 26, 331, 23, 23);

		light2 = new Lumiere(Color.GREEN, Color.RED, 420, 406, 23, 23);
		sensor2 = new Lumiere(Color.YELLOW, Color.BLACK, 343, 459, 23, 23);

		mapLight.put(1, light0);
		mapLight.put(2, light1);
		mapLight.put(3, light2);

		mapSensor.put(1, sensor0);
		mapSensor.put(2, sensor1);
		mapSensor.put(3, sensor2);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(circuit, 0, 0, circuit.getWidth(this),
				circuit.getHeight(this), this);

		Lumiere light0 = mapLight.get(0);
		Lumiere light1 = mapLight.get(1);
		Lumiere light2 = mapLight.get(2);
		Lumiere sensor0 = mapSensor.get(0);
		Lumiere sensor1 = mapSensor.get(1);
		Lumiere sensor2 = mapSensor.get(2);

		g.setColor(light0.getColor());
		g.fillOval(light0.getX(), light0.getY(), light0.getWidth(),
				light0.getHeight());

		g.setColor(sensor0.getColor());
		g.fillOval(sensor0.getX(), sensor0.getY(), sensor0.getWidth(),
				sensor0.getHeight());

		g.setColor(light1.getColor());
		g.fillOval(light1.getX(), light1.getY(), light1.getWidth(),
				light1.getHeight());

		g.setColor(sensor1.getColor());
		g.fillOval(sensor1.getX(), sensor1.getY(), sensor1.getWidth(),
				sensor1.getHeight());

		g.setColor(light2.getColor());
		g.fillOval(light2.getX(), light2.getY(), light2.getWidth(),
				light2.getHeight());

		g.setColor(sensor2.getColor());
		g.fillOval(sensor2.getX(), sensor2.getY(), sensor2.getWidth(),
				sensor2.getHeight());

		for (Map.Entry<Integer, Integer> entry : mapPos.entrySet()) {
			int posTrain = entry.getValue();
			Image train2 = ImageTool.rotate(train, listPosAngle[2][posTrain]);
			g.drawImage(train2, listPosAngle[0][posTrain],
					listPosAngle[1][posTrain], train2.getWidth(this),
					train2.getHeight(this), this);
		}

	}

	public void addTrain(int trainId, int pos) {
		mapPos.put(trainId, pos);
	}

	public void addLight(int lightId) {

	}

	public void step(int trainId) {
		Integer i = mapPos.get(trainId);
		if (i == listPosAngle[0].length - 1)
			i = 0;
		else
			i++;
		mapPos.put(trainId, i);
		repaint();
	}

	public JPanel getComponent() {
		return this;
	}

	public void switchLight(int lightId) {
		Lumiere l = mapLight.get(lightId);
		if (l.isOn())
			l.off();
		else
			l.on();
		mapLight.put(lightId, l);
		repaint();
	}

	@Override
	public void notifyUp(final int sensorId) {
		final Lumiere l = mapSensor.get(sensorId);
		if (l.isOn())
			l.off();
		else
			l.on();
		mapSensor.put(sensorId, l);
		repaint();
		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (l.isOn())
					l.off();
				else
					l.on();
				mapSensor.put(sensorId, l);
				repaint();
			}
		};

		Thread t = new Thread(r);
		t.start();
	}

}
