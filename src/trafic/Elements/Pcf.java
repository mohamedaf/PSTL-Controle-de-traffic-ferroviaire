package trafic.Elements;

import java.util.ArrayList;

public class Pcf {

    private int reqid;
    private String type;
    private Scenario scenario;
    private Topography topography;
    private Init init;
    private Lights lights;
    private final ArrayList<Info> info;

    public Pcf() {
	this.info = new ArrayList<Info>();
    }

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

    public int getReqid() {
	return reqid;
    }

    public void setReqid(int reqid) {
	this.reqid = reqid;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Scenario getScenario() {
	return scenario;
    }

    public void setScenario(Scenario scenario) {
	this.scenario = scenario;
    }

    public Topography getTopography() {
	return topography;
    }

    public void setTopography(Topography topography) {
	this.topography = topography;
    }

    public Init getInit() {
	return init;
    }

    public void setInit(Init init) {
	this.init = init;
    }

    public Lights getLights() {
	return lights;
    }

    public void setLights(Lights lights) {
	this.lights = lights;
    }

    public ArrayList<Info> getInfoList() {
	return info;
    }

    public void addInfo(Info i) {
	info.add(i);
    }

    public Info getInfo(int i) {
	return info.get(i);
    }

    public String toString() {
	return "reqid : " + reqid + "\nType : " + type + "\n"
		+ scenario.toString() + topography.toString() + init.toString()
		+ lights.toString();
    }
}
