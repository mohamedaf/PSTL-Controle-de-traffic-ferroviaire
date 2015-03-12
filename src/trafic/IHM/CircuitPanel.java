package trafic.IHM;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;

public class CircuitPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4268679183602296707L;
	Image circuit;
	Image train;
	HashMap<Integer, Dimension> listPos;
	Lumiere light1, light2, light3;
	Lumiere sensor1, sensor2, sensor3;
	private int trainPosX, trainPosY;

	public CircuitPanel(String circuit, String train) {
		this.circuit = Toolkit.getDefaultToolkit().getImage(circuit);
		this.train = Toolkit.getDefaultToolkit().getImage(train);
		this.train = ImageTool.resize(this.train,
				this.train.getWidth(this) / 8, this.train.getHeight(this) / 8);
		listPos = new HashMap<Integer, Dimension>();
		setMinimumSize(new Dimension(512, 512));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 512));

		light1 = new Lumiere(Color.GREEN, Color.RED, 245, 74, 23, 23);
		sensor1 = new Lumiere(Color.YELLOW, Color.BLACK, 312, 85, 23, 23);

		light2 = new Lumiere(Color.GREEN, Color.RED, 73, 404, 23, 23);
		sensor2 = new Lumiere(Color.YELLOW, Color.BLACK, 26, 331, 23, 23);

		light3 = new Lumiere(Color.GREEN, Color.RED, 420, 406, 23, 23);
		sensor3 = new Lumiere(Color.YELLOW, Color.BLACK, 343, 459, 23, 23);

		trainPosX = 222;
		trainPosY = 49;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(circuit, 0, 0, circuit.getWidth(this),
				circuit.getHeight(this), this);

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

		g.setColor(light3.getColor());
		g.fillOval(light3.getX(), light3.getY(), light3.getWidth(),
				light3.getHeight());

		g.setColor(sensor3.getColor());
		g.fillOval(sensor3.getX(), sensor3.getY(), sensor3.getWidth(),
				sensor3.getHeight());

		g.drawImage(ImageTool.rotate(train, 90), trainPosX, trainPosY,
				train.getWidth(this), train.getHeight(this), this);
	}

}
