package trafic.elements;

import java.util.ArrayList;

public class Lights {
    private ArrayList<Light> lights;

    public Lights() {
	lights = new ArrayList<Light>();
    }

    public void addLight(Light l) {
	lights.add(l);
    }

    public Light getLight(int i) {
	return lights.get(i);
    }

    public ArrayList<Light> getListLights() {
	return lights;
    }

    @Override
    public String toString() {
	String s = "";
	for (Light l : lights) {
	    s += l.toString() + "\n";
	}
	return s;
    }

}
