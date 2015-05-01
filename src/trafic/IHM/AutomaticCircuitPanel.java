package trafic.IHM;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import trafic.elements.Pcf;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.interfaces.ICircuitPanel;

public class AutomaticCircuitPanel extends JPanel implements ICircuitPanel {
	private static final long serialVersionUID = -8723221582013220745L;
	private HashMap<Integer, Lumiere> sensorList = new HashMap<Integer, Lumiere>();
	private HashMap<Integer, Lumiere> lightList = new HashMap<Integer, Lumiere>();
	private ArrayList<Line2D.Double> linesList = new ArrayList<Line2D.Double>();
	private Point center;

	public AutomaticCircuitPanel(int width, int height, Pcf circuit) {
		int centerX = width / 2;
		int centerY = height / 2;
		/* Center */
		center = new Point(centerX, centerY);
		/* Center */
		int radius = Math.min(width, height) / 2 * 4 / 5;
		int sensorNumbers = circuit.getTopography().getSensorEdgesList().size();
		double angle = 2 * Math.PI / sensorNumbers;
		int cpt = 0;
		int pointH = height / 50, pointW = width / 50;

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
			sensorList.put(id, new Lumiere(Color.YELLOW, Color.BLACK, x, y,
					pointW, pointH, id));

			if (circuit.getLights().getLightById(se.getCapteur().getId()) != null) {
				x2 = (int) (centerX + radius
						* Math.cos(cpt * angle + angle / 3));
				y2 = (int) (centerY + radius
						* Math.sin(cpt * angle + angle / 3));

				lightList.put(id, new Lumiere(Color.GREEN, Color.RED, x2, y2,
						pointW, pointH, id));
			} else {
				x2 = x;
				y2 = y;
			}
			cpt++;

			System.out.println("x : " + x + " y : " + y + " x2 : " + x2
					+ " y2 : " + y2);
		}

		for (SensorEdges sEdges : circuit.getTopography().getSensorEdgesList()) {
			id = sEdges.getCapteur().getId();
			Lumiere lSensor = sensorList.get(id);
			Lumiere lLight = lightList.get(id);
			for (Sensor s : sEdges.getCapteurOutList()) {
				if (lLight != null) {
					x = lLight.getX() + lLight.getWidth() / 2;
					y = lLight.getY() + lLight.getHeight() / 2;
					linesList.add(new Line2D.Double(x, y, lSensor.getX()
							+ lSensor.getWidth() / 2, lSensor.getY()
							+ lSensor.getHeight() / 2));
				} else {
					x = lSensor.getX() + lSensor.getWidth() / 2;
					y = lSensor.getY() + lSensor.getHeight() / 2;
				}
				Lumiere tmp = sensorList.get(s.getId());
				x2 = tmp.getX() + tmp.getWidth() / 2;
				y2 = tmp.getY() + tmp.getHeight() / 2;
				linesList.add(new Line2D.Double(x, y, x2, y2));
			}
		}

		repaint();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Map.Entry<Integer, Lumiere> entry : sensorList.entrySet()) {
			Lumiere l = entry.getValue();
			g.setColor(l.getColor());
			g.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		}
		for (Map.Entry<Integer, Lumiere> entry : lightList.entrySet()) {
			Lumiere l = entry.getValue();
			g.setColor(l.getColor());
			g.fillOval(l.getX(), l.getY(), l.getWidth(), l.getHeight());
		}
		g.setColor(Color.BLACK);
		for (Line2D.Double ld : linesList) {
			g.drawLine((int) ld.x1, (int) ld.y1, (int) ld.x2, (int) ld.y2);
		}
		g.fillOval(center.x, center.y, 20, 20);
	}

	@Override
	public void step(int trainId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTrain(int trainId, int pos) {
		// TODO Auto-generated method stub

	}

	@Override
	public JPanel getComponent() {
		return this;
	}

	@Override
	public void switchLight(int lightId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyUp(int sensorId) {
		// TODO Auto-generated method stub

	}

}
