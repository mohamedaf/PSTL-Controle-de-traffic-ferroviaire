package trafic.interfaces;

/**
 * @author KOBROSLI - AFFES
 *
 */
public interface StartableStoppable {

    /**
     * Methode initialisant notre controlleur et demandant le demarrage du
     * circuit
     */
    public void start();

    /**
     * Methode arretant le circuit
     */
    public void stop();

}
