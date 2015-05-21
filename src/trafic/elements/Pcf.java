package trafic.elements;

import java.util.ArrayList;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Pcf
 */
public class Pcf {

    private int reqid;
    private String type;
    private Scenario scenario;
    private Topography topography;
    private Init init;
    private Lights lights;
    private final ArrayList<Info> info;

    /**
     * Constructeur
     */
    public Pcf() {
	this.info = new ArrayList<Info>();
    }

    /**
     * Constructeur
     * 
     * @param reqid
     *            : identifiant de la balise PCF
     * @param type
     *            : type de la balise (request | advise | answer)
     * @param scenario
     *            : scenario
     * @param topography
     *            : topography
     * @param init
     *            : init
     * @param lights
     *            : lights
     */
    public Pcf(int reqid, String type, Scenario scenario,
	    Topography topography, Init init, Lights lights) {
	this.reqid = reqid;
	this.type = type;
	this.scenario = scenario;
	this.topography = topography;
	this.init = init;
	this.lights = lights;
	this.info = new ArrayList<Info>();
    }

    /**
     * Retourne l'identifiant de la balise
     * 
     * @return reqid
     */
    public int getReqid() {
	return reqid;
    }

    /**
     * Modifie l'identifiant de la balise
     * 
     * @param reqid
     *            : nouveau reqid
     */
    public void setReqid(int reqid) {
	this.reqid = reqid;
    }

    /**
     * Retourne le type de la balise (request | advise | answer)
     * 
     * @return type
     */
    public String getType() {
	return type;
    }

    /**
     * Modifie le type de la balise
     * 
     * @param type
     *            : type de la balise (request | advise | answer)
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * 
     * @return scenario
     */
    public Scenario getScenario() {
	return scenario;
    }

    /**
     * Modifie le champ scenario
     * 
     * @param scenario
     *            : nouveau scenario
     */
    public void setScenario(Scenario scenario) {
	this.scenario = scenario;
    }

    /**
     * Retourne le champ topography
     * 
     * @return topography
     */
    public Topography getTopography() {
	return topography;
    }

    /**
     * Modifie le champ topography
     * 
     * @param topography
     *            : nouvelle topography
     */
    public void setTopography(Topography topography) {
	this.topography = topography;
    }

    /**
     * Retourne le champ init
     * 
     * @return init
     */
    public Init getInit() {
	return init;
    }

    /**
     * Modifie le champ init
     * 
     * @param init
     *            : nouvelle valeur de le champ init
     */
    public void setInit(Init init) {
	this.init = init;
    }

    /**
     * Retourne la liste de feux
     * 
     * @return lights
     */
    public Lights getLights() {
	return lights;
    }

    /**
     * Modifie le champ lights
     * 
     * @param lights
     *            : nouvelle valeur de le champ lights
     */
    public void setLights(Lights lights) {
	this.lights = lights;
    }

    /**
     * Retourne la liste de messages Info contenues
     * 
     * @return info
     */
    public ArrayList<Info> getInfoList() {
	return info;
    }

    /**
     * Ajouter un element a la liste info
     * 
     * @param i
     *            : element a ajouter a la liste
     */
    public void addInfo(Info i) {
	info.add(i);
    }

    /**
     * Retourne l'element a l'indexe i de la liste info
     * 
     * @param i
     *            : indexe de l'element a retourner
     * @return element a l'indexe i de la liste info
     */
    public Info getInfo(int i) {
	return info.get(i);
    }

    @Override
    public String toString() {
	return "reqid : " + reqid + "\nType : " + type + "\n"
		+ scenario.toString() + topography.toString() + init.toString()
		+ lights.toString();
    }
}
