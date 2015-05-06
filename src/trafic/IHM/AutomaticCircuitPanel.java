package trafic.IHM;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.interfaces.ICircuitPanel;

public class AutomaticCircuitPanel extends JPanel implements ICircuitPanel {
	private static final long serialVersionUID = -8723221582013220745L;
	private HashMap<Integer, Lumiere> sensorMap = new HashMap<Integer, Lumiere>();
	private HashMap<Integer, Lumiere> lightMap = new HashMap<Integer, Lumiere>();
	private ArrayList<Line2D.Double> linesList = new ArrayList<Line2D.Double>();
	private Image train;
	private ArrayList<TrainIhm> trainsList = new ArrayList<TrainIhm>();
	private int pointSize;

	public AutomaticCircuitPanel(int width, int height, Pcf circuit,
			String train) {

		try {
			this.train = ImageIO.read(new File(train));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.train = ImageTool
				.resize(this.train, this.train.getWidth(this) / 12,
						this.train.getHeight(this) / 12);

		int centerX = width / 2;
		int centerY = height / 2;

		int radius = Math.min(width, height) / 2 * 4 / 5;
		int sensorNumbers = circuit.getTopography().getSensorEdgesList().size();
		double angle = 2 * Math.PI / sensorNumbers;
		int cpt = 0;
		int pointH = height / 50, pointW = width / 50;
		pointSize = pointH + 5;

		int x = 0, y = 0, x2 = -1, y2 = -1;
		int id;

		setBackground(Color.WHITE);

		setAlignmentX(Component.LEFT_ALIGNMENT);
		setPreferredSize(new Dimension(width, height));

		System.out.println("Center X : " + centerX + " center Y : " + centerY);

		for (SensorEdges se : circuit.getTopography().getSensorEdgesList()) {

			x = (int) (centerX + radius * Math.cos(cpt * angle));
			y = (int) (centerY + radius * Math.sin(cpt * angle));
			id = se.getCapteur().getId();
			sensorMap.put(id, new Lumiere(Color.YELLOW, Color.BLACK, x, y,
					pointW, pointH, id));

			if (circuit.getLights().getLightById(se.getCapteur().getId()) != null) {
				x2 = (int) (centerX + radius
						* Math.cos(cpt * angle + angle / 3));
				y2 = (int) (centerY + radius
						* Math.sin(cpt * angle + angle / 3));

				lightMap.put(id, new Lumiere(Color.GREEN, Color.RED, x2, y2,
						pointW, pointH, id));
			} else {
				x2 = x;
				y2 = y;
			}
			cpt++;
		}

		for (SensorEdges sEdges : circuit.getTopography().getSensorEdgesList()) {
			id = sEdges.getCapteur().getId();
			Lumiere lSensor = sensorMap.get(id);
			Lumiere lLight = lightMap.get(id);
			for (Sensor s : sEdges.getCapteurOutList()) {
				if (lLight != null) {
					x = lLight.getX() + lLight.getWidth() / 2;
					y = lLight.getY() + lLight.getHeight() / 2;
					Line2D.Double line = new Line2D.Double(x, y, lSensor.getX()
							+ lSensor.getWidth() / 2, lSensor.getY()
							+ lSensor.getHeight() / 2);
					linesList.add(line);

				} else {
					x = lSensor.getX() + lSensor.getWidth() / 2;
					y = lSensor.getY() + lSensor.getHeight() / 2;
				}
				Lumiere tmp = sensorMap.get(s.getId());
				x2 = tmp.getX() + tmp.getWidth() / 2;
				y2 = tmp.getY() + tmp.getHeight() / 2;
				Line2D.Double ll = new Line2D.Double(x, y, x2, y2);
				linesList.add(ll);

				
				System.out.println("Lien entre "+id+" et "+s.getId());
			}
		}

		for (Position p : circuit.getInit().getListPositions()) {
			Lumiere lSensorBefore;
			Lumiere lLightBefore = lightMap.get(p.getBefore().getId());
			Lumiere lSensorAfter = sensorMap.get(p.getAfter().getId());
			if (lLightBefore == null) {
				lSensorBefore = sensorMap.get(p.getBefore().getId());
				x = lSensorBefore.getX();
				y = lSensorBefore.getY();
			} else {
				x = lLightBefore.getX();
				y = lLightBefore.getY();
			}
			x2 = lSensorAfter.getX();
			y2 = lSensorAfter.getY();

			Point point = lineCenter(x, y, x2, y2);

			TrainIhm tmp = new TrainIhm(new Lumiere(Color.CYAN, Color.ORANGE,
					point.x, point.y, pointSize, pointSize), p, pointSize);
			trainsList.add(tmp);
			SwitchLightThread slt = new SwitchLightThread(tmp, this);
			slt.start();
		}

		repaint();
	}

	private Point lineCenter(int x1, int y1, int x2, int y2) {
		int x = (int) ((x2 + x1) / 2);
		int y = (int) ((y2 + y1) / 2);
		return new Point(x, y);
	}

	private float getAngle(Point o, Point target) {
		float angle = (float) Math.toDegrees(Math.atan2(target.y - o.y,
				target.x - o.x));

		if (angle < 0) {
			angle += 360;
		}

		return angle;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Map.Entry<Integer, Lumiere> entry : sensorMap.entrySet()) {
			Lumiere l = entry.getValue();
			g.setColor(l.getColor());
			g.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
			g.drawString(l.getId() + "", l.getX() + 0, l.getY() - 10);
		}
		for (Map.Entry<Integer, Lumiere> entry : lightMap.entrySet()) {
			Lumiere l = entry.getValue();
			g.setColor(l.getColor());
			g.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		}
		g.setColor(Color.BLACK);
		for (Line2D.Double ld : linesList) {
			g.drawLine((int) ld.x1, (int) ld.y1, (int) ld.x2, (int) ld.y2);
		}
		for (TrainIhm tt : trainsList) {
			g.setColor(tt.getColor());
			g.fillOval(tt.getX(), tt.getY(), tt.getSize(), tt.getSize());
		}

	}

	@Override
	public void step(int trainId) {
		int x1, y1, x2, y2;
		Lumiere lSensorBefore, lLightBefore, lSensorAfter, lLightAfter;
		Position pos;
		int beforeId, afterId;
		for (TrainIhm t : trainsList) {
			pos = t.getPos();

			if (trainId == pos.getTrain().getId()) {

				beforeId = pos.getBefore().getId();
				afterId = pos.getAfter().getId();

				lLightBefore = lightMap.get(beforeId);
				lSensorAfter = sensorMap.get(afterId);
				if (lLightBefore == null) {
					lSensorBefore = sensorMap.get(beforeId);
					x1 = lSensorBefore.getX();
					y1 = lSensorBefore.getY();
				} else {
					x1 = lLightBefore.getX();
					y1 = lLightBefore.getY();
				}
				x2 = lSensorAfter.getX();
				y2 = lSensorAfter.getY();

				Point point = lineCenter(x1, y1, x2, y2);

				if (point.x == t.getX() && point.y == t.getY()) {
					System.out.println("IHM : Le train " + trainId
							+ " vient de depasser un capteur.");
					lLightAfter = lightMap.get(afterId);
					if (lLightAfter == null) {
						System.out
								.println("IHM : Il n'y a pas de feu associe au capteur");
						return;
					}
					System.out
							.println("IHM : Le train "
									+ trainId
									+ " est entre le capteur et le feu qui lui est associe");
					x1 = lLightAfter.getX();
					y1 = lLightAfter.getY();
				} else {
					System.out.println("IHM : Le train " + trainId
							+ " est entre dans un nouveau canton");
				}
				point = lineCenter(x2, y2, x1, y1);

				t.setX(point.x);
				t.setY(point.y);

				repaint();
				break;
			}
		}

	}

	@Override
	public JPanel getComponent() {
		return this;
	}

	@Override
	public void switchLight(int lightId) {
		Lumiere l = lightMap.get(lightId);
		if (l.isOn())
			l.off();
		else
			l.on();
		repaint();
	}

	@Override
	public void notifyUp(int sensorId) {
		/* On recupere le sensor active pour le changer de couleur */
		final Lumiere l = sensorMap.get(sensorId);
		if (l.isOn())
			l.off();
		else
			l.on();
		repaint();

		/* La lumiere reste allumee pendant 1 seconde, puis s'eteint */
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (l.isOn())
					l.off();
				else
					l.on();
				repaint();
			}
		};

		Thread t = new Thread(r);
		t.start();
	}

}