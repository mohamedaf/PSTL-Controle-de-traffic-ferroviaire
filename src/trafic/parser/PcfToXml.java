package trafic.parser;

import java.util.ArrayList;

import trafic.elements.Sensor;
import trafic.elements.Info;
import trafic.elements.Init;
import trafic.elements.Light;
import trafic.elements.Lights;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Scenario;
import trafic.elements.SensorEdges;
import trafic.elements.Topography;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

public class PcfToXml {

    public String buildXml(Pcf pcf) {

	return pcf(pcf.getReqid(), pcf.getType(), pcf.getScenario(),
		pcf.getTopography(), pcf.getInit(), pcf.getLights(),
		pcf.getInfoList());
    }

    public static String pcf(int reqid, String type, Scenario scenario,
	    Topography topography, Init init, Lights lights,
	    ArrayList<Info> info) {
	String s;

	s = "<pcf reqid=\"" + reqid + "\" type=\"" + type + "\">";

	if (scenario != null)
	    s += scenario(scenario.getId());

	if (topography != null)
	    s += topography(topography.getSensorEdgesList());

	if (init != null)
	    s += init(init.getListPositions());

	if (lights != null)
	    s += lights(lights.getListLights());

	if (!info.isEmpty())
	    for (Info i : info) {
		s += info(i.getStatus(), i.getChaine());
	    }

	s += "</pcf>";

	return s;
    }

    public static String scenario(int id) {
	return "<scenario id=\"" + id + "\" />";
    }

    public static String topography(ArrayList<SensorEdges> SensorEdgesList) {
	String s;

	s = "<topography>";

	if (!SensorEdgesList.isEmpty())
	    for (SensorEdges se : SensorEdgesList) {
		s += sensorEdges(se.getCapteur(), se.getCapteurIn(),
			se.getCapteurOut());
	    }

	s += "</topography>";

	return s;
    }

    public static String sensorEdges(Sensor c, Sensor cIn, Sensor cOut) {
	String s;

	s = capteur(c.getId(), c.getType());

	s += "<in>";
	s += capteur(cIn.getId(), cIn.getType());
	s += "</in>";

	s += "<out>";
	s += capteur(cOut.getId(), cOut.getType());
	s += "</out>";

	return s;
    }

    public static String capteur(int id, String type) {
	return "<capteur id=\"" + id + "\" type=" + type + " />";
    }

    public static String init(ArrayList<Position> positions) {
	String s;

	s = "<init>";

	if (!positions.isEmpty())
	    for (Position p : positions) {
		s += position(p.getBefore(), p.getAfter(), p.getTrain());
	    }

	s += "</init>";

	return s;
    }

    public static String position(Sensor before, Sensor after, Train train) {
	String s;

	s = "<position>";

	s += "<before>";
	s += capteur(before.getId(), before.getType());
	s += "</before>";

	s += train(train.getId(), train.getAction(), train.getDirection());

	s += "<after>";
	s += capteur(after.getId(), after.getType());
	s += "</after>";

	s += "</position>";

	return s;
    }

    public static String train(int id, TrainAction action,
	    TrainDirection direction) {
	return "<train id=\"" + id + "\" action=\"" + action
		+ "\" direction=\"" + direction + "\" />";
    }

    public static String lights(ArrayList<Light> lights) {
	String s;

	s = "<lights>";

	if (!lights.isEmpty())
	    for (Light l : lights) {
		s += light(l.getId(), l.getColor());
	    }

	s += "</lights>";

	return s;
    }

    public static String light(int id, Color color) {
	return "<light id=\"" + id + "\" color=\"" + color + "\" />";
    }

    public static String info(Status status, String chaine) {
	return "<info status=\"" + status + "\"> " + chaine + " </info>";
    }

}
