package trafic.test;

import trafic.chemin.Chemin;
import trafic.chemin.SensorNotFoundException;
import trafic.elements.Pcf;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.elements.Topography;
import trafic.enums.SensorType;

/**
 * @author KOBROSLI - AFFES
 *
 *         Classe de test
 */
public class CheminTest {

    public static void main(String[] args) {

	Pcf circuit = new Pcf();
	Topography topo = new Topography();
	Sensor s1 = new Sensor(1, SensorType.canton);
	Sensor s2 = new Sensor(2, SensorType.canton);
	Sensor s3 = new Sensor(3, SensorType.canton);
	Sensor s4 = new Sensor(4, SensorType.canton);
	Sensor s5 = new Sensor(5, SensorType.canton);
	Sensor s6 = new Sensor(6, SensorType.canton);

	SensorEdges se1 = new SensorEdges(s1, s4, s2);
	se1.setCapteurOut(s3);
	se1.setCapteurIn(s5);

	SensorEdges se2 = new SensorEdges(s2, s1, s6);

	SensorEdges se3 = new SensorEdges(s3, s1, s4);
	se3.setCapteurOut(s5);

	SensorEdges se4 = new SensorEdges(s4, s3, s1);

	SensorEdges se5 = new SensorEdges(s5, s3, s6);

	SensorEdges se6 = new SensorEdges(s6, s5, s1);
	se6.setCapteurIn(s5);
	se6.setCapteurIn(s2);

	topo.addSensorEdges(se1);
	topo.addSensorEdges(se2);
	topo.addSensorEdges(se3);
	topo.addSensorEdges(se4);
	topo.addSensorEdges(se5);
	topo.addSensorEdges(se6);

	circuit.setTopography(topo);

	Chemin c;
	try {
	    c = new Chemin(1, Chemin.genererEnchainement(circuit));
	    System.out.println(c.toString());

	    Chemin c1 = new Chemin(1, Chemin.genererEnchainement(circuit, 1));
	    System.out.println(c1.toString());

	    Chemin c2 = new Chemin(1, Chemin.genererEnchainement(circuit, 1));
	    System.out.println(c2.toString());
	} catch (SensorNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
