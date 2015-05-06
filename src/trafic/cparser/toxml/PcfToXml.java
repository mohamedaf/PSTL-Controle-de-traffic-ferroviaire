package trafic.cparser.toxml;

import java.util.ArrayList;

import trafic.elements.Info;
import trafic.elements.Init;
import trafic.elements.Light;
import trafic.elements.Lights;
import trafic.elements.Pcf;
import trafic.elements.Position;
import trafic.elements.Scenario;
import trafic.elements.Sensor;
import trafic.elements.SensorEdges;
import trafic.elements.Topography;
import trafic.elements.Train;
import trafic.enums.Color;
import trafic.enums.SensorType;
import trafic.enums.Status;
import trafic.enums.TrainAction;
import trafic.enums.TrainDirection;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe transformant recursivement un objet Pcf en une chaine
 *         contenant le texte XML equivalent
 */
public class PcfToXml {

    /**
     * Construit recursivement l'equivalent XML de l'objet pcf
     * 
     * @param pcf
     *            : objet pcf a transformer
     * @return une chaine XMLcontenant le resultat final
     */
    public String buildXml(Pcf pcf) {

	return pcf(pcf.getReqid(), pcf.getType(), pcf.getScenario(),
		pcf.getTopography(), pcf.getInit(), pcf.getLights(),
		pcf.getInfoList());
    }

    /**
     * Methode parsant une balise PCF
     * 
     * @param reqid
     *            : reqid de la balise
     * @param type
     *            : type de la balise
     * @param scenario
     *            : scenario du circuit
     * @param topography
     *            : topography du circuit
     * @param init
     *            : configuration initiale du circuit
     * @param lights
     *            : feux du circuit
     * @param info
     *            : balise info contenant les informations a transmettre
     * @return chaine representant une balise PCF ainsi que son contenu et donc
     *         les balises imriquees existantes
     */
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

    /**
     * Methode parsant une balise Scenario
     * 
     * @param id
     *            : identifiant de la balise
     * @return chaine representant une balise scenario ainsi que son contenu
     */
    public static String scenario(int id) {
	return "<scenario id=\"" + id + "\" />";
    }

    /**
     * Methode parsant une balise topography
     * 
     * @param SensorEdgesList
     *            : liste contenant les capteurs de la balise sensor-edges
     * @return chaine representant une balise topography ainsi que son contenu
     */
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

    /**
     * Methode parsant une balise sensorEdges
     * 
     * @param c
     *            : capteur
     * @param cIn
     *            : capteur In
     * @param cOut
     *            : capteur Out
     * @return chaine representant une balise sensorEdges ainsi que son contenu
     */
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

    /**
     * Methode parsant une balise capteur
     * 
     * @param id
     *            : identifiant du capteur
     * @param type
     *            : (canton | station)
     * @return chaine representant une balise capteur ainsi que son contenu
     */
    public static String capteur(int id, SensorType type) {
	return "<capteur id=\"" + id + "\" type=" + type + " />";
    }

    /**
     * Methode parsant une balise init
     * 
     * @param positions
     *            : positions de base a l'initialisation du circuit
     * @return chaine representant une balise init ainsi que son contenu
     */
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

    /**
     * Methode parsant une balise position
     * 
     * @param before
     *            : premier capteur precedant le train
     * @param after
     *            : premier capteur se trouvant devant le train
     * @param train
     *            : le train concerne
     * @return chaine representant une balise position ainsi que son contenu
     */
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

    /**
     * Methode parsant une balise train
     * 
     * @param id
     *            : identifiant du train
     * @param action
     *            : (start | stop)
     * @param direction
     *            : (forward | backward)
     * @return chaine representant une balise train ainsi que son contenu
     */
    public static String train(int id, TrainAction action,
	    TrainDirection direction) {
	return "<train id=\"" + id + "\" action=\"" + action
		+ "\" direction=\"" + direction + "\" />";
    }

    /**
     * Methode parsant une balise lights
     * 
     * @param lights
     *            : liste des feux
     * @return chaine representant une balise lights ainsi que son contenu
     */
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

    /**
     * Methode parsant une balise light
     * 
     * @param id
     *            : identifiant du feu
     * @param color
     *            : (red | green)
     * @return chaine representant une balise light ainsi que son contenu
     */
    public static String light(int id, Color color) {
	return "<light id=\"" + id + "\" color=\"" + color + "\" />";
    }

    /**
     * Methode parsant une balise info
     * 
     * @param status
     *            : (ok | ko)
     * @param chaine
     *            : contenu de l'information
     * @return chaine representant une balise info ainsi que son contenu
     */
    public static String info(Status status, String chaine) {
	return "<info status=\"" + status + "\"> " + chaine + " </info>";
    }

}