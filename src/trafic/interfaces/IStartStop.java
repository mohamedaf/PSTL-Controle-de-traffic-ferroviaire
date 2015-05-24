package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface IStartStop {

    /**
     * Methode initialisant notre controlleur et demandant le demarrage du
     * circuit
     */
    public void start(String address, int port);

    /**
     * Methode arretant le circuit
     */
    public void stop();

}
