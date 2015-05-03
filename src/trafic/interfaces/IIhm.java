package trafic.interfaces;

import trafic.elements.Pcf;

/**
 * @author KOBROSLI - AFFES
 *
 *         Interface de l'IHM
 */
public interface IIhm {

    public void notifyInit(Pcf circuit);

    public void switchLight(int lightId);

    public void notifyUp(int sensorId);

    public void step(int idTrain);

}
