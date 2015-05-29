package trafic.interfaces;

import javax.swing.JPanel;

/**
 * @author KOBROSLI - AFFES Interface du circuit de l'IHM
 */
public interface ICircuitPanel {

    /**
     * Indique que le train a avance d'un pas
     * 
     * @param trainId
     *            Id du train qui avance
     */
    public void step(int trainId);

    /**
     * @return le composant actuel
     */
    public JPanel getComponent();

    /**
     * Change la couleur d'un feu
     * 
     * @param lightId
     *            Id du feu dont on change la couleur
     */
    void switchLight(int lightId);

    /**
     * Notifie d'un up du capteur
     * 
     * @param sensorId
     *            Capteur active
     */
    public void notifyUp(int sensorId);
}
