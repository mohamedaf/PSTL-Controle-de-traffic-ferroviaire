package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IUpNotifier {

    /**
     * Notifie que le circuit est initialise
     */
    public void notifyInit();

    /**
     * Notifie qu'un capteur a ete active
     * 
     * @param sensorId
     *            : Numero du capteur active
     */
    public void notifyUp(int sensorId);

}
