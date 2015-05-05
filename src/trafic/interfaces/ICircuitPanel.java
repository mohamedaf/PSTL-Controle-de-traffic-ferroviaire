package trafic.interfaces;

import javax.swing.JPanel;

/**
 * @author KOBROSLI - AFFES Interface du circuit de l'IHM
 */
public interface ICircuitPanel {

    public void step(int trainId);

    public JPanel getComponent();

    public void switchLight(int lightId);

    public void notifyUp(int sensorId);
}
