package trafic.elements;

import java.util.ArrayList;

/**
 * 
 * @author KOBROSLI - AFFES
 *
 *         Classe representant la balise Lights
 */
public class Lights {
    private ArrayList<Light> lights;

    /**
     * Constructeur
     */
    public Lights() {
	lights = new ArrayList<Light>();
    }

    /**
     * ajoute un feu a la liste
     * 
     * @param l
     *            : feu
     */
    public void addLight(Light l) {
	lights.add(l);
    }

    /**
     * 
     * @param i
     *            : indexe du feu
     * @return : retourne le feu se trouvant a l'indexe i de la liste
     */
    public Light getLight(int i) {
	return lights.get(i);
    }

    /**
     * 
     * @param id
     *            : identifiant du feu
     * @return : retourne le feu correspondant a l'identifiant id dans la liste
     */
    public Light getLightById(int id) {
	for (Light l : lights)
	    if (l.getId() == id)
		return l;
	return null;
    }

    /**
     * 
     * @return retourne la liste de feux
     */
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
